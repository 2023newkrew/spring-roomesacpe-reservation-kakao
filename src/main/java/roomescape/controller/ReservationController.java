package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import java.net.URI;

@Controller
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponseDto> findReservation(@PathVariable("id") Long id) {
        ReservationResponseDto reservationResponseDto = reservationService.findReservation(id);
        return ResponseEntity.ok(reservationResponseDto);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto req) {
        Reservation reservation = reservationService.createReservation(req);
        ReservationResponseDto res = reservationService.getReservationDto(reservation);
        String id = res.getId().toString();
        return ResponseEntity.created(URI.create("/reservations").resolve(id)).body(res);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity cancelReservation(@PathVariable("id") Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
