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
    public ResponseEntity<ReservationResponseDto> findReservation(@PathVariable("id") Long reservationId) {
        ReservationResponseDto reservationResponseDto = reservationService.findReservation(reservationId);
        return ResponseEntity.ok(reservationResponseDto);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto dto) {
        Reservation reservation = reservationService.createReservation(dto);
        ReservationResponseDto res = new ReservationResponseDto(reservation);
        return ResponseEntity.created(URI.create("/reservations").resolve(res.getId().toString())).body(res);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity cancelReservation(@PathVariable("id") Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
