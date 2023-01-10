package nextstep.controller;

import nextstep.Reservation;
import nextstep.ReservationInfo;
import nextstep.Theme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.created(URI.create("/reservations/" + counter.incrementAndGet())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity showReservation(@PathVariable Long id) {
        ReservationInfo reservationInfo = new ReservationInfo(
                1L,
                LocalDate.of(2022, 8, 11),
                LocalTime.of(13, 0),
                "name",
                new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));
        return ResponseEntity.ok().body(reservationInfo);
    }
}
