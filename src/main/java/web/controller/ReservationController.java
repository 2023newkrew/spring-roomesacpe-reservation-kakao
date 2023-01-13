package web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import web.domain.Reservation;
import web.domain.Theme;
import web.dto.request.ReservationRequestDTO;
import web.dto.response.ReservationIdDto;
import web.dto.response.ReservationResponseDTO;
import web.exception.NoSuchReservationException;
import web.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationService reservationService;

    public ReservationController(final JdbcTemplate jdbcTemplate, final ReservationService reservationService) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {

        ReservationIdDto reservationIdDto = reservationService.createReservation(reservationRequestDTO);

        return ResponseEntity.created(URI.create("/reservations/" + reservationIdDto.getId())).build();
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
