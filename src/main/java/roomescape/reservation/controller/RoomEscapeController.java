package roomescape.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;
import roomescape.theme.domain.Theme;

import javax.validation.Valid;
import java.net.URI;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class RoomEscapeController {

    private static final int INDEX_ID = 1;
    private static final int INDEX_DATE = 2;
    private static final int INDEX_TIME = 3;
    private static final int INDEX_NAME = 4;
    private static final int INDEX_THEME_NAME = 5;
    private static final int INDEX_THEME_DESC = 6;
    private static final int INDEX_THEME_PRICE = 7;
    private static final String CHECK_DUP_QUERY = "SELECT EXISTS(SELECT * FROM RESERVATION WHERE date = ? AND time = ?);";
    private static final String INSERT_QUERY = "INSERT INTO RESERVATION (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String LOOKUP_QUERY = "SELECT * FROM RESERVATION WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM RESERVATION WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final ReservationService reservationService;

    public RoomEscapeController(JdbcTemplate jdbcTemplate, ReservationService reservationService) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<String> createReservation(@RequestBody @Valid Reservation reservation) {
        Long reservationId = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> lookUpReservation(@PathVariable("id") String reservationId) {
        Reservation reservation = jdbcTemplate.queryForObject(
                LOOKUP_QUERY,
                (lookUpRs, rowNum) -> {
                    Long id = lookUpRs.getLong(INDEX_ID);
                    LocalDate date = LocalDate.parse(lookUpRs.getString(INDEX_DATE));
                    LocalTime time = LocalTime.parse(lookUpRs.getString(INDEX_TIME));
                    String name = lookUpRs.getString(INDEX_NAME);
                    String themeName = lookUpRs.getString(INDEX_THEME_NAME);
                    String themeDesc = lookUpRs.getString(INDEX_THEME_DESC);
                    Integer themePrice = lookUpRs.getInt(INDEX_THEME_PRICE);
                    Theme theme = new Theme(themeName, themeDesc, themePrice);
                    return new Reservation(id, date, time, name, theme);
                },
                Long.parseLong(reservationId));
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String id) {
        jdbcTemplate.update(DELETE_QUERY, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
