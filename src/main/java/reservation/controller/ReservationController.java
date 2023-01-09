package reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.domain.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @PostMapping("")
    public ResponseEntity<Reservation> postReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long reservation_id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long reservation_id) {
        return ResponseEntity.ok().build();
    }
}
