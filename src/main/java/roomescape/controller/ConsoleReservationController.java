package roomescape.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.service.Reservation.ReservationService;

import javax.validation.Valid;

@Controller
public class ConsoleReservationController {
    final ReservationService reservationService;
    private static final Logger logger =
            LoggerFactory.getLogger(ConsoleReservationController.class);

    public ConsoleReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Reservation createReservation(@RequestBody @Valid Reservation reservation){
        logger.info("Request Create Reservation - " + reservation.toMessage());
        Reservation userReservation = reservationService.createReservation(reservation);
        logger.info("Finish Create Reservation - " + userReservation.toMessage());
        return userReservation;
    }

    public Reservation lookUpReservation(@PathVariable("id") Long reservationId) {
        logger.info("Request lookUp Reservation, Id: " + reservationId);
        Reservation userReservation = reservationService.lookUpReservation(reservationId);
        logger.info("Finish lookUp Reservation " + userReservation.toMessage());
        return userReservation;
    }

    public void deleteReservation(@PathVariable("id") Long deleteId) {
        logger.info("Request delete Reservation, Id: " + deleteId);
        reservationService.deleteReservation(deleteId);
        logger.info("Finish delete Reservation Id: " + deleteId);
    }
}
