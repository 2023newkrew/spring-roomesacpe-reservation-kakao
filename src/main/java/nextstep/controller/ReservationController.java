package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Object> reserve(@RequestBody Reservation reservation){
        Reservation confirmedReservation = reservationService.reserve(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + confirmedReservation.getId())).build();
    }
}
