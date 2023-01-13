package roomescape.reservation.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;

import java.net.URI;
import roomescape.reservation.service.RoomEscapeService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final RoomEscapeService roomEscapeService;
    public ReservationController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @PostMapping()
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequestDto requestDto) {
        Long id = roomEscapeService.makeReservation(requestDto);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> findById(@PathVariable Long id) {
        ReservationResponseDto responseDto = roomEscapeService.findReservationById(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        roomEscapeService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
