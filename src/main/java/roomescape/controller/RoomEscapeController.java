package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomEscapeController {
    List<Reservation> reservationList = new ArrayList<>();

    //https://localhost:8080/reservations  / post data{}

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid Reservation reservation){
        // TODO: check duplicated time

        reservationList.add(reservation);
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
