package nextstep.controller;

import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.response.ReservationResponse;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.ReservationNotFoundException;
import nextstep.service.RoomEscapeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservations")
@RestController
public class RoomEscapeController {

    private final RoomEscapeService roomEscapeService;

    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @PostMapping("")
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody CreateReservationRequest createReservationRequest) throws DuplicateReservationException {
        ReservationResponse reservationResponse = roomEscapeService.createReservationForWeb(createReservationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + reservationResponse.getId())
                .body(reservationResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findReservation(@PathVariable Long id) throws ReservationNotFoundException {
        return ResponseEntity
                .ok(roomEscapeService.findReservationForWeb(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        roomEscapeService.cancelReservation(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
