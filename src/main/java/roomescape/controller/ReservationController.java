package roomescape.controller;

import nextstep.Reservation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
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
    public ResponseEntity createReservation(@RequestBody ReservationRequestDto dto) {
        Long reservationId = reservationService.createReservation(dto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }
}
