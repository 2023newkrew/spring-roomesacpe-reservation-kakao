package roomescape.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger =
            LoggerFactory.getLogger(WebReservationController.class);

    @Autowired
    public WebReservationController(@Qualifier("WebReservation") ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<String> createReservation(@RequestBody @Valid Reservation reservation){
        logger.info("Request Create Reservation - " + reservation.toMessage());
        Reservation userReservation = reservationService.createReservation(reservation);
        logger.info("Finish Create Reservation - " + userReservation.toMessage());
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .body(userReservation.createMessage(userReservation.getId()));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<String> lookUpReservation(@PathVariable("id") String reservationId) {
        logger.info("Request lookUp Reservation, Id: " + reservationId);
        Reservation userReservation = reservationService.lookUpReservation(Long.valueOf(reservationId));
        logger.info("Finish lookUp Reservation " + userReservation.toMessage());
        return ResponseEntity.ok().body(userReservation.toMessage());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String deleteId) {
        logger.info("Request delete Reservation, Id: " + deleteId);
        reservationService.deleteReservation(Long.valueOf(deleteId));
        logger.info("Finish delete Reservation Id: " + deleteId);
        return ResponseEntity.noContent().build();
    }
}
