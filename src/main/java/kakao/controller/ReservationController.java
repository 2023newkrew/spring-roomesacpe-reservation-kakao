package kakao.controller;

import kakao.controller.request.ReservationRequest;
import kakao.controller.response.ReservationResponse;
import kakao.service.ReservationService;
import lombok.RequiredArgsConstructor;
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
        Long reservationId = reservationService.book(reservationRequest);
        String location = "/reservations/" + reservationId.toString();

        return ResponseEntity.created(URI.create(location)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.lookUp(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.cancel(id);

        return ResponseEntity.noContent().build();
    }
}