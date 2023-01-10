package roomescape.controller;

import nextstep.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/reservations/{id}")
    public ResponseEntity showReservation(@PathVariable Long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElse(null);

        if(findReservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 예약은 없는 예약입니다.다");
        }

        return ResponseEntity.ok(findReservation);
    }

}
