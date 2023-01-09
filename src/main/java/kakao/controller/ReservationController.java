package kakao.controller;

import kakao.dto.request.CreateReservationRequest;
import kakao.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController()
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<Void> createReservation(@RequestBody CreateReservationRequest request) {
        reservationService.createReservation(request);
        return ResponseEntity.created(URI.create("")).build();
    }
}
