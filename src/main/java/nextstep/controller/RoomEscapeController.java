package nextstep.controller;

import nextstep.Reservation;
import nextstep.dto.CreateReservationRequest;
import nextstep.service.RoomEscapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservations")
@RestController
public class RoomEscapeController {

    private final RoomEscapeService roomEscapeService;

    @Autowired
    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @PostMapping("")
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        Reservation reservation = roomEscapeService.add(createReservationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservation.getId())
                .body(reservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findReservation(@PathVariable Long id) {
        return ResponseEntity
                .ok(roomEscapeService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        roomEscapeService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}