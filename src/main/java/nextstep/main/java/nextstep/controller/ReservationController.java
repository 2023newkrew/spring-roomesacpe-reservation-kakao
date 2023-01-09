package nextstep.main.java.nextstep.controller;

import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<?> create(@RequestBody ReservationCreateRequestDto request) {
        reservationService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
