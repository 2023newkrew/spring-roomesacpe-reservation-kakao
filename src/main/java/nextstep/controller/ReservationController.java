package nextstep.controller;

import nextstep.Reservation;
import nextstep.ReservationInfo;
import nextstep.Theme;
import nextstep.exceptions.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final AtomicLong counter = new AtomicLong();
    private final List<Reservation> reservations = new ArrayList<>();
    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        long id = counter.incrementAndGet();
        Reservation newReservation = new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), theme);

        boolean existsReservation = reservations.stream()
                .anyMatch(r -> r.getDate().equals(reservation.getDate())
                        && r.getTime().equals(reservation.getTime()));
        if (existsReservation) {
            throw new CustomException();
        }

        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity showReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow();
        ReservationInfo reservationInfo = new ReservationInfo(reservation);
        return ResponseEntity.ok().body(reservationInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow();
        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
