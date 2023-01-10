package nextstep.controller;

import nextstep.Reservation;
import nextstep.dto.ReservationDto;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("")
    public ResponseEntity reserve(@RequestBody Reservation reservation) {
        Long id = reservationService.reserve(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ReservationDto retrieve(@PathVariable Long id) {
        return reservationService.retrieve(id);
    }
}