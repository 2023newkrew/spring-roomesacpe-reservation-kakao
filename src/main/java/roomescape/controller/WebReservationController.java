package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.service.Reservation.ReservationService;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class WebReservationController {
    final ReservationService reservationService;
    @Autowired
    public WebReservationController(@Qualifier("WebReservation") ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<String> createReservation(@RequestBody @Valid Reservation reservation){
        Reservation userReservation = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .body(userReservation.createMessage(userReservation.getId()));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<String> lookUpReservation(@PathVariable("id") String reservationId) {
        Reservation userReservation = reservationService.lookUpReservation(Long.valueOf(reservationId));
        return ResponseEntity.ok().body(userReservation.toMessage());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String deleteId) {
        reservationService.deleteReservation(Long.valueOf(deleteId));
        return ResponseEntity.noContent().build();
    }
}
