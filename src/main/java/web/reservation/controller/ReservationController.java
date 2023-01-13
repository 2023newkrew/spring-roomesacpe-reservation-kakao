package web.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.exception.ErrorCode;
import web.reservation.dto.ReservationRequestDto;
import web.reservation.dto.ReservationResponseDto;
import web.reservation.exception.ReservationException;
import web.reservation.service.ReservationService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;

@RequestMapping("/reservations")
@RequiredArgsConstructor
@RestController
public class ReservationController {

    public static final LocalTime BEGIN_TIME = LocalTime.of(11, 0, 0);
    public static final LocalTime LAST_TIME = LocalTime.of(20, 30, 0);

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> reservation(@RequestBody @Valid ReservationRequestDto requestDto) {
        checkOutOfBusinessHours(requestDto.getTime());
        checkUnitOf30Minutes(requestDto.getTime());

        long createdId = reservationService.reservation(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/reservations/" + createdId))
                .build();
    }

    private void checkOutOfBusinessHours(LocalTime time) {
        if (time.isBefore(BEGIN_TIME) || time.isAfter(LAST_TIME)) {
            throw new ReservationException(ErrorCode.OUT_OF_BUSINESS_HOURS);
        }
    }

    private void checkUnitOf30Minutes(LocalTime time) {
        if (time.getMinute() % 30 != 0) {
            throw new ReservationException(ErrorCode.NOT_UNIT_OF_30_MINUTES);
        }
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDto> findReservationById(@PathVariable long reservationId) {
        ReservationResponseDto responseDto = reservationService.findReservationById(reservationId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent()
                .build();
    }
}
