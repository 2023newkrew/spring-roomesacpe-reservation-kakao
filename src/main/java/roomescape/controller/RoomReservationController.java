package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.RoomReservationService;

@RestController
@RequestMapping("/reservations")
public class RoomReservationController {
    private final RoomReservationService roomReservationService;

    public RoomReservationController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(
            @RequestBody ReservationRequest reservationRequest
    ) {
        roomReservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservation(
            @PathVariable Long reservationId
    ) {
        ReservationResponse reservationResponse = roomReservationService.getReservation(reservationId);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId
    ) {
        roomReservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
