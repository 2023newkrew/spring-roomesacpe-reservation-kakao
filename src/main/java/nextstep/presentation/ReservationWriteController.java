package nextstep.presentation;

import nextstep.dto.request.CreateReservationRequest;
import nextstep.service.ReservationWriteService;
import nextstep.utils.ReservationRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationWriteController {

    private final ReservationWriteService reservationWriteService;
    private final ReservationRequestValidator reservationRequestValidator;

    public ReservationWriteController(ReservationWriteService reservationWriteService, ReservationRequestValidator reservationRequestValidator) {
        this.reservationWriteService = reservationWriteService;
        this.reservationRequestValidator = reservationRequestValidator;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        reservationRequestValidator.validateCreateRequest(createReservationRequest);
        Long reservationId = reservationWriteService.createReservation(createReservationRequest);

        return ResponseEntity.created(URI.create("/reservations/" + reservationId))
                .build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long reservationId) {
        reservationWriteService.deleteReservationById(reservationId);

        return ResponseEntity.noContent()
                .build();
    }

}
