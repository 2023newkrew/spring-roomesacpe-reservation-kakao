package nextstep.reservation.controller;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.Reservation;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.reservation.service.ReservationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

}
