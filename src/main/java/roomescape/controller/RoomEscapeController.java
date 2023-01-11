package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import javax.validation.Valid;
import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomEscapeController {
    List<Reservation> reservationList = new ArrayList<>();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid Reservation reservation){

        jdbcTemplate.update(
            "INSERT INTO RESERVATION (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)",
                    Date.valueOf(reservation.getDate()),
                    Time.valueOf(reservation.getTime()),
                    reservation.getName(),
                    reservation.getTheme().getName(),
                    reservation.getTheme().getDesc(),
                    reservation.getTheme().getPrice()
        );
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> lookUpReservation(@PathVariable("id") String id) {
        Reservation reservation = reservationList.stream().filter(reserve -> reserve.getId() == Long.parseLong(id))
                .findAny().orElse(null);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String id) {
        reservationList.removeIf(reserve -> reserve.getId() == Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
