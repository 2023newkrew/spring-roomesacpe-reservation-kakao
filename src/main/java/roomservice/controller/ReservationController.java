package roomservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomservice.domain.Reservation;
import roomservice.repository.ReservationDao;
import roomservice.service.ReservationService;

import java.net.URI;

/**
 * ReservationController processes various HTTP requests
 * related to reservations, including create, show, and delete methods.
 */
@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Add a reservation to this program.
     * @param reservation reservation to be added.
     * @return "created" response with id resulted by the program.
     */
    @PostMapping()
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        Long id = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    /**
     * show a reservation which have specific id.
     * @param id which you want to find.
     * @return "ok" response with reservation if successfully found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> showReservation(@PathVariable Long id) {
        return ResponseEntity.ok().body(reservationService.findReservation(id));
    }

    /**
     * Delete a reservation from this program.
     * @param id which you want to delete.
     * @return reservation if successfully deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
