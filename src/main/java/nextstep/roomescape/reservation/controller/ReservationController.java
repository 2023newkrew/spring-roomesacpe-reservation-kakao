package nextstep.roomescape.reservation.controller;

import nextstep.roomescape.reservation.service.ReservationService;
import nextstep.roomescape.reservation.controller.dto.ReservationResponseDTO;
import nextstep.roomescape.reservation.model.Reservation;
import nextstep.roomescape.reservation.controller.dto.ReservationRequestDTO;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequestDTO reservation) {
        ReservationResponseDTO createReservation;
        createReservation = reservationService.create(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + createReservation.getId())).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponseDTO> findReservation(@PathVariable Long id) {
        ReservationResponseDTO reservation = reservationService.findById(id);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler
    public ResponseEntity<String> handle(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
