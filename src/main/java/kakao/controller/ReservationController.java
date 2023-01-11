package kakao.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import kakao.domain.Reservation;
import kakao.dto.request.CreateReservationRequest;
import kakao.dto.response.ReservationResponse;
import kakao.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
@Validated
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        ReservationResponse response = new ReservationResponse(reservationService.createReservation(request));
        return ResponseEntity.created(URI.create("")).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable("id") @Min(1L) Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteReservation(@PathVariable("id") @Min(1L) Long id) {
        int deletedCount = reservationService.deleteReservation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedCount);
    }
}
