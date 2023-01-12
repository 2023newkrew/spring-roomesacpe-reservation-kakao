package nextstep.reservation.controller;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.CreateReservationException;
import nextstep.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        Reservation createReservation;
        createReservation = reservationService.create(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + createReservation.getId())).build();
    }

    @DeleteMapping("")
    public ResponseEntity<Reservation> clear() {
        reservationService.clear();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CreateReservationException.class)
    public ResponseEntity<String> handle(CreateReservationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
