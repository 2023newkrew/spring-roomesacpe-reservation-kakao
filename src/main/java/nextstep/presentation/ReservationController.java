package nextstep.presentation;

import nextstep.dto.CreateReservationRequest;
import nextstep.dto.FindReservationResponse;
import nextstep.exception.InvalidCreateReservationRequestException;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(
            @Validated @RequestBody CreateReservationRequest createReservationRequest,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new InvalidCreateReservationRequestException();

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
