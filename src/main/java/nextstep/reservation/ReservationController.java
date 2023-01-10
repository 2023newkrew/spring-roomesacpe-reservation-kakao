package nextstep.reservation;

import nextstep.reservation.exception.CreateReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping("/reservations")
    public ResponseEntity createReservation(@RequestBody Reservation reservation) {
        Reservation createReservation;
        try {
            createReservation = reservationService.create(reservation);
        } catch (CreateReservationException e) {
            throw new CreateReservationException();
        }
        return ResponseEntity.created(URI.create("/reservations/" + createReservation.getId())).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> findReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(CreateReservationException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.badRequest().body("CreateReservationException");
    }
}
