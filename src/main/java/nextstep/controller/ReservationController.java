package nextstep.controller;

import nextstep.domain.reservation.Reservation;
import nextstep.domain.dto.CreateReservationDTO;
import nextstep.domain.reservation.Reservations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("")
public class ReservationController {
    @PostMapping("/reservations")
    public ResponseEntity createReservation(@RequestBody CreateReservationDTO reservationDto) {
        Reservation reservation = new Reservation(
                reservationDto.getLocalDate(),
                reservationDto.getLocalTime(),
                reservationDto.getName()
        );

        Reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/"+reservation.getId())).build();
    }
}
