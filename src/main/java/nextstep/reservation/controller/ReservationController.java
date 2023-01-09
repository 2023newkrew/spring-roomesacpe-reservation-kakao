package nextstep.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.reservation.dto.ReservationResponseDto;
import nextstep.reservation.service.ReservationService;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Object> addReservation(@ModelAttribute ReservationRequestDto requestDto) {
        reservationService.addReservation(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/reservations/1");

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable Long id) {

        return ResponseEntity.ok().body(reservationService.getReservation(id));
    }

}
