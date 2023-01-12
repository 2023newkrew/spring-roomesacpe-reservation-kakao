package kakao.controller;

import kakao.model.request.ReservationRequest;
import kakao.model.response.ReservationResponse;
import kakao.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Long reservationId = reservationService.createReservation(reservationRequest);
        String location = "/reservations/" + reservationId.toString();

        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}