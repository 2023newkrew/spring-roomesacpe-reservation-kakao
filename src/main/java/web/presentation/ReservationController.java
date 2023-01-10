package web.presentation;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.dto.request.ReservationRequestDTO;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        Long id = 1L;
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }
}
