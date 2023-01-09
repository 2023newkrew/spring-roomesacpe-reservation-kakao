package nextstep.main.java.nextstep.controller;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.ReservationCreateRequestDto;
import nextstep.main.java.nextstep.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(reservationService.findOneById(id), HttpStatus.OK);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        reservationService.deleteOneById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
