package roomescape.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;

import java.net.URI;
import roomescape.reservation.service.ReservationService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping()
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequestDto requestDto) {
        Long id = reservationService.makeReservation(requestDto);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> findById(@PathVariable Long id) {
        ReservationResponseDto responseDto = reservationService.findReservationById(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
