package nextstep.controller;

import nextstep.Reservation;
import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.response.ReservationResponse;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
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
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody CreateReservationRequest createReservationRequest) throws DuplicateReservationException {
        Reservation reservation = roomEscapeService.add(createReservationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservation.getId())
                .body(ReservationResponse.fromEntity(reservation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findReservation(@PathVariable Long id) throws ReservationNotFoundException {
        return ResponseEntity
                .ok(ReservationResponse.fromEntity(roomEscapeService.get(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        roomEscapeService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
