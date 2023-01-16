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

import static roomescape.utils.Messages.*;

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
        logger.info(CREATE_REQUEST.getMessage() + reservation.toMessage());
        try {
            Reservation userReservation = reservationService.createReservation(reservation);
            logger.info(CREATE_RESPONSE.getMessage() + userReservation.toMessage());
            return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                    .body(userReservation.createMessage(userReservation.getId()));
        } catch (Exception e){
            logger.error(String.valueOf(e));
            return ResponseEntity.badRequest().body(RESERVATION_CREATE_ERROR.getMessage());
        }
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<String> lookUpReservation(@PathVariable("id") String reservationId) {
        logger.info(LOOKUP_REQUEST.getMessage() + reservationId);
        try {
            Reservation userReservation = reservationService.lookUpReservation(Long.valueOf(reservationId));
            logger.info(LOOKUP_RESPONSE.getMessage() + userReservation.toMessage());
            return ResponseEntity.ok().body(userReservation.toMessage());
        } catch (Exception e){
            logger.error(String.valueOf(e));
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") String deleteId) {
        logger.info(DELETE_REQUEST.getMessage() + deleteId);
        try {
            reservationService.deleteReservation(Long.valueOf(deleteId));
            logger.info(DELETE_RESPONSE.getMessage() + deleteId);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            logger.error(String.valueOf(e));
            return ResponseEntity.notFound().build();
        }
    }
}
