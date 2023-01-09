package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<URI> createReservation(@RequestBody ReservationRequestDto requestDto) {
        int createdId = reservationService.createReservation(requestDto);

        return ResponseEntity.created(URI.create("/reservations/" + createdId))
                .build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationResponseDto> findReservation(@PathVariable Long id) {
        ReservationResponseDto reservation = reservationService.findReservation(id);

        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent()
                .build();
    }
}
