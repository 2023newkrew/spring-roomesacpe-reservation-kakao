package nextstep.controller;

import lombok.RequiredArgsConstructor;
import nextstep.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    @PostMapping
    public ResponseEntity<?> createReservation() {

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<?> getReservation(@PathVariable("reservation_id") Long reservationId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("reservation_id") Long reservationId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
