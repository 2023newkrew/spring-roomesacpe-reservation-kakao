package reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservation.domain.Reservation;
import reservation.service.ReservationService;
import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity postReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/users/" + reservation.getId())).build();
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long reservation_id) {
        Reservation reservation = reservationService.getReservation(reservation_id);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity deleteReservation(@PathVariable Long reservation_id) {
        reservationService.deleteReservation(reservation_id);
        return ResponseEntity.ok().build();
    }
}
