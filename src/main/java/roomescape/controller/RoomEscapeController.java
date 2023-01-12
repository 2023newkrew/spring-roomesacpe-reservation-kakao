package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import javax.validation.Valid;
import java.net.URI;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;

@RestController
public class RoomEscapeController {
    List<Reservation> reservationList = new ArrayList<>();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/reservations")
public ResponseEntity<Reservation> createReservation(@RequestBody @Valid Reservation reservation){
        Integer duplicatedRow = jdbcTemplate.queryForObject(
                "select count(*) from RESERVATION where date = ? AND time = ?", Integer.class,
                reservation.getDate(), reservation.getTime());
        if (duplicatedRow == 1) {
            System.out.println("ASASASS");
            throw new IllformedLocaleException("TEST");
        }
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
    public ResponseEntity<Reservation> lookUpReservation(@PathVariable("id") String reservationId) {
        Reservation reservation = jdbcTemplate.queryForObject(
                "select date, time, name, theme_name, theme_desc, theme_price from RESERVATION where id = ?",
                new Object[]{reservationId},
                new RowMapper<Reservation>() {
                    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
                        LocalDate date = LocalDate.parse(rs.getString("date"));
                        LocalTime time = LocalTime.parse(rs.getString("time"));
                        String name = rs.getString("name");
                        String themeName = rs.getString("Theme_name");
                        String themeDesc = rs.getString("Theme_desc");
                        Integer themePrice = rs.getInt("Theme_price");
                        Theme theme = new Theme(themeName, themeDesc, themePrice);
                        return new Reservation(Long.parseLong(reservationId), date, time, name, theme);
                    }
                });
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String id) {
        jdbcTemplate.update("DELETE FROM RESERVATION WHERE id=?", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
