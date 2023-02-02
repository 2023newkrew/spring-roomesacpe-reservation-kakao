package nextstep.controller;

import lombok.RequiredArgsConstructor;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequestDTO request) {
        URI location = URI.create(RESERVATION_PATH + "/" + service.create(request));

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable("reservation_id") Long reservationId) {
        return ResponseEntity.ok(service.getById(reservationId));
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity deleteReservation(@PathVariable("reservation_id") Long reservationId) {
        service.deleteById(reservationId);

        return ResponseEntity.noContent()
                .build();
    }
}
