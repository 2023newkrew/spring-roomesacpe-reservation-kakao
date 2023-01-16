package roomescape.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class RoomEscapeController {
    private final ReservationService reservationService;

    public RoomEscapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<String> createReservation(@RequestBody @Valid Reservation reservation) {
        Long reservationId = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> findReservationById(@PathVariable("id") String reservationId) {
        Reservation reservation = reservationService.findById(reservationId);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String reservationId) {
        reservationService.deleteById(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
