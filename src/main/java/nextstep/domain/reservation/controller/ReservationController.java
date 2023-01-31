package nextstep.domain.reservation.controller;

import nextstep.domain.reservation.dto.ReservationRequestDto;
import nextstep.domain.reservation.dto.ReservationResponseDto;
import nextstep.domain.reservation.service.ReservationService;
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
    public ResponseEntity<Void> reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        Long id = reservationService.reserve(reservationRequestDto);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/{id}")
    public ReservationResponseDto retrieve(@PathVariable Long id) {
        return reservationService.retrieve(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}