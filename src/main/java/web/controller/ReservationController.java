package web.controller;

import java.net.URI;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    private final Theme defaultTheme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Reservation reservation = reservationRequestDTO.toEntity(defaultTheme);

        String selectSql = "SELECT id FROM reservation WHERE date = (?) AND time = (?) LIMIT 1 ";

        List<Long> ids = jdbcTemplate.query(selectSql, ((rs, rowNum) ->
                rs.getLong("id")), Date.valueOf(reservation.getDate()), Time.valueOf(reservation.getTime()));

        if (ids.size() > 0) {
            throw new DuplicatedReservationException();
        }

        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final Theme theme = reservation.getTheme();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, theme.getName());
            ps.setString(5, theme.getDesc());
            ps.setInt(6, theme.getPrice());
            return ps;
        }, keyHolder);

        return ResponseEntity.created(URI.create("/reservations/" + keyHolder.getKey())).build();
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

        List<Reservation> reservations = jdbcTemplate.query(selectSql, ((rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(rs.getString("theme_name"),
                        rs.getString("theme_desc"),
                        rs.getInt("theme_price")
                ))), id);

        if (reservations.size() == 0) {
            throw new NoSuchReservationException();
        }
        return reservations.get(0);
    }
}
