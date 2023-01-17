package nextstep.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.exception.RoomEscapeException;
import nextstep.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;


    @PostMapping
    public ResponseEntity<Object> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse registeredReservation = reservationService.registerReservation(reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + registeredReservation.getId())).build();
    }

    @DeleteMapping
    public ResponseEntity<ReservationResponse> clear() {
        reservationService.clear();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findReservation(@PathVariable Long id) {
        ReservationResponse reservation = reservationService.findById(id);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<String> handle(RoomEscapeException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
