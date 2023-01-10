package roomescape.controller;

import nextstep.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {

    private static List<Reservation> reservations = new ArrayList<>();
    private static Long count = 0L;

    @PostMapping("/reservations")
    public ResponseEntity createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = new Reservation(reservationRequest);
        Reservation overlapReservation = reservations.stream()
                .filter(reservation -> reservation.overlap(newReservation))
                .findFirst()
                .orElse(null);
        if (overlapReservation != null) {
            return ResponseEntity.unprocessableEntity().body("이미 예약된 시간입니다.");
        }

        newReservation.setId(++count);
        reservations.add(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + count)).build();
    }

}
