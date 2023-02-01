package nextstep.controller;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.dto.ReservationRequestDTO;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private static final String RESERVATION_PATH = "/reservations";

    private final ReservationService service;

    @PostMapping
    public ResponseEntity<Exception> createReservation(@RequestBody ReservationRequestDTO request) {
        try {
            URI location = URI.create(RESERVATION_PATH + "/" + service.create(request));

            return ResponseEntity.created(location)
                    .build();
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("reservation_id") Long reservationId) {

        return ResponseEntity.ok(service.getById(reservationId));
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservation_id") Long reservationId) {
        service.deleteById(reservationId);

        return ResponseEntity.noContent()
                .build();
    }
}
