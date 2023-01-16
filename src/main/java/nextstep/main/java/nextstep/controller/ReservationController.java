package nextstep.main.java.nextstep.controller;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.message.ControllerMessage;
import nextstep.main.java.nextstep.service.ReservationService;
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

    @PostMapping()
    public ResponseEntity<Reservation> create(@RequestBody ReservationCreateRequestDto request) {
        Reservation createdReservation = reservationService.save(request);
        return ResponseEntity.created(URI.create("/reservations/" + createdReservation.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findOneById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        reservationService.deleteOneById(id);
        return ResponseEntity.ok(ControllerMessage.DELETE_RESERVATION_SUCCESS_MESSAGE);
    }
}
