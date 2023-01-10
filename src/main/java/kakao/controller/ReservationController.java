package kakao.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController()
@RequestMapping("/reservations")
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<Long> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        long generatedId = reservationService.createReservation(request);
        return ResponseEntity.created(URI.create("")).body(generatedId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable("id") @Min(1L) Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") @Min(1L) Long id) {
        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
