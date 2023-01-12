package roomescape.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.net.URI;
import roomescape.service.RoomEscapeService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final RoomEscapeService roomEscapeService;
    public ReservationController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @PostMapping()
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDto requestDto) {
        long id = roomEscapeService.makeReservation(requestDto);
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
