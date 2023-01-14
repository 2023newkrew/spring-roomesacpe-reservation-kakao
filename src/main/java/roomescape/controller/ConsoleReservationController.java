package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;
import roomescape.service.Reservation.ReservationService;

import javax.validation.Valid;

@Controller
public class ConsoleReservationController {
    final ReservationService reservationService;

    public ConsoleReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Reservation createReservation(@RequestBody @Valid Reservation reservation){
        Reservation userReservation = reservationService.createReservation(reservation);
        return userReservation;
    }

    public Reservation lookUpReservation(@PathVariable("id") Long reservationId) {
        Reservation userReservation = reservationService.lookUpReservation(reservationId);
        return userReservation;
    }

    public boolean deleteReservation(@PathVariable("id") Long deleteId) {
        reservationService.deleteReservation(deleteId);
        return true;
    }
}
