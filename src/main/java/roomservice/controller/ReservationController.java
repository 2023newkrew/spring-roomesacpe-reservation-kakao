package roomservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomservice.domain.Reservation;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @PostMapping()
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        Long id = 1L;
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> showReservation(@PathVariable Long id){
        return ResponseEntity.ok().body(new Reservation(
                1L,
                LocalDate.now(),
                LocalTime.now(),
                "hi",
                null
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }
}
