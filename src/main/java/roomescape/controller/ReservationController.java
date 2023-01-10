package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;
import java.net.URI;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponseDto> findReservation(@PathVariable("id") Long reservationId) {
        ReservationResponseDto reservationResponseDto = reservationService.findReservation(reservationId);
        return ResponseEntity.ok(reservationResponseDto);
    }

    @PostMapping("/reservations")
    public ResponseEntity createReservation(@RequestBody ReservationRequestDto dto) {
        Long reservationId = reservationService.createReservation(dto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity cancelReservation(@PathVariable("id") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
