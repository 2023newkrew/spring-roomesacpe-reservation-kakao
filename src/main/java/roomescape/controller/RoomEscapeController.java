package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class RoomEscapeController {
    final ReservationService reservationService;
    @Autowired
    public RoomEscapeController(@Qualifier("WebService") ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
        public ResponseEntity<String> createReservation(@RequestBody @Valid Reservation reservation){
        String userMessage = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(userMessage);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<String> lookUpReservation(@PathVariable("id") String reservationId) {
        String userMessage = reservationService.lookUpReservation(Long.valueOf(reservationId));
        return ResponseEntity.ok().body(userMessage);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String deleteId) {
        reservationService.deleteReservation(Long.valueOf(deleteId));
        return ResponseEntity.noContent().build();
    }
}
