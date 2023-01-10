package nextstep.controller;

import nextstep.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/reservations")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.created(URI.create("/reservations/" + counter.incrementAndGet())).build();
    }
}
