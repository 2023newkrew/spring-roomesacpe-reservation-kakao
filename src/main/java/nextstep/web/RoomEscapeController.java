package nextstep.web;

import nextstep.model.Reservation;
import nextstep.web.dto.ReservationRequest;
import nextstep.web.dto.ReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class RoomEscapeController {
    private final RoomEscapeService roomEscapeService;

    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest request) {
        Reservation reservation = roomEscapeService.createReservation(request);
        return ResponseEntity.created(URI.create("/reservations/"+ reservation.getId())).build();
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        Reservation reservation = roomEscapeService.getReservation(id);
        return ResponseEntity.ok(new ReservationResponse(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        roomEscapeService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
