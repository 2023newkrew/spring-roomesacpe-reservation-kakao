package web.presentation;

import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.domain.Reservation;
import web.domain.Theme;
import web.dto.request.ReservationRequestDTO;
import web.dto.response.ReservationResponseDTO;
import web.exception.DuplicatedReservationException;
import web.exception.NoSuchReservationException;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final Theme defaultTheme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationController(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRequestDTO.toEntity(defaultTheme);

        String selectSql = "SELECT id FROM reservation WHERE date = (?) AND time = (?) LIMIT 1";

        List<Long> ids = jdbcTemplate.query(selectSql, ((rs, rowNum) ->
                rs.getLong("id")), Date.valueOf(reservation.getDate()), Time.valueOf(reservation.getTime()));

        if (!ids.isEmpty()) {
            throw new DuplicatedReservationException();
        }

        long newReservationId = this.insertActor.executeAndReturnKey(reservation.buildParams()).longValue();

        return ResponseEntity.created(URI.create("/reservations/" + newReservationId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> retrieveReservation(@PathVariable Long id) {
        Reservation reservation = getReservationById(id);

        final ReservationResponseDTO reservationResponseDTO = ReservationResponseDTO.from(reservation);

        return ResponseEntity.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(reservationResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        getReservationById(id);

        String deleteSql = "DELETE FROM reservation WHERE id = (?)";
        this.jdbcTemplate.update(deleteSql, id);

        return ResponseEntity.noContent().build();
    }

    private Reservation getReservationById(final Long id) throws NoSuchReservationException {
        String selectSql = "SELECT * FROM reservation WHERE id = (?) LIMIT 1 ";

        List<Reservation> reservations = jdbcTemplate.query(selectSql, (rs, rowNum) -> Reservation.from(rs), id);

        if (reservations.isEmpty()) {
            throw new NoSuchReservationException();
        }
        return reservations.get(0);
    }
}
