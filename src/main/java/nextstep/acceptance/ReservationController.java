package nextstep.acceptance;

import nextstep.dto.CreateReservationRequest;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(CreateReservationRequest createReservationRequest) {
        Long reservationId = reservationService.createReservation(createReservationRequest);

        return ResponseEntity.created(URI.create("/reservations/" + reservationId))
                .build();
    }

}
