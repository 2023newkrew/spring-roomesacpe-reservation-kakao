package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationInfo;
import nextstep.domain.ReservationSaveForm;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity createReservation(@RequestBody ReservationSaveForm reservationSaveForm) {
        Long id = reservationService.saveReservation(reservationSaveForm);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity showReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findReservation(id);
        ReservationInfo reservationInfo = new ReservationInfo(reservation);
        return ResponseEntity.ok().body(reservationInfo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
