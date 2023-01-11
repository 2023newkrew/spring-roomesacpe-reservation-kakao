package roomservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomservice.domain.Reservation;
import roomservice.repository.ReservationDao;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }

    @PostMapping()
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        long id = reservationDao.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservationDao.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reservationDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
