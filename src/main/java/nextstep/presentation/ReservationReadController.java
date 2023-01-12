package nextstep.presentation;

import nextstep.dto.response.FindReservationResponse;
import nextstep.service.ReservationReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationReadController {

    private final ReservationReadService reservationReadService;

    public ReservationReadController(ReservationReadService reservationReadService) {
        this.reservationReadService = reservationReadService;
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<FindReservationResponse> findReservationById(@PathVariable Long reservationId) {
        FindReservationResponse findReservationResponse = reservationReadService.findReservationById(reservationId);

        return ResponseEntity.ok(findReservationResponse);
    }

}
