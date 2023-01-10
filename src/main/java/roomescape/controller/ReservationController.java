package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationRequestDto;

import java.net.URI;

@Controller
public class ReservationController {

    @PostMapping("/reservations")
    public ResponseEntity createReservation(@RequestBody ReservationRequestDto dto) {
        Long id = 1L;
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }
}
