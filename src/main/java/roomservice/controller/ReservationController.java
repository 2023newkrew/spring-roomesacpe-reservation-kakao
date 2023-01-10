package roomservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomservice.domain.Reservation;
import roomservice.repository.ReservationDao;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDao reservationDao = new ReservationDao();

    @PostMapping()
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        Long id = reservationDao.insertReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> showReservation(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservationDao.selectReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
