package nextstep.presentation;

import nextstep.dto.request.CreateReservationRequest;
import nextstep.dto.response.FindReservationResponse;
import nextstep.service.ReservationService;
import nextstep.utils.ReservationRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRequestValidator reservationRequestValidator;

    public ReservationController(ReservationService reservationService, ReservationRequestValidator reservationRequestValidator) {
        this.reservationService = reservationService;
        this.reservationRequestValidator = reservationRequestValidator;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        reservationRequestValidator.validateCreateRequest(createReservationRequest);
        Long reservationId = reservationService.createReservation(createReservationRequest);

        return ResponseEntity.created(URI.create("/reservations/" + reservationId))
                .build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<FindReservationResponse> findReservationById(@PathVariable Long reservationId) {
        FindReservationResponse findReservationResponse = reservationService.findReservationById(reservationId);

        return ResponseEntity.ok(findReservationResponse);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long reservationId) {
        reservationService.deleteReservationById(reservationId);

        return ResponseEntity.noContent()
                .build();
    }

}
