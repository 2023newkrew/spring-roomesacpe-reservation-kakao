package web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.ReservationRequestDto;
import web.dto.ReservationResponseDto;
import web.exception.ReservationException;
import web.service.RoomEscapeService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;

import static web.exception.ErrorCode.NOT_UNIT_OF_30_MINUTES;
import static web.exception.ErrorCode.OUT_OF_BUSINESS_HOURS;

@RequestMapping("/reservations")
@RequiredArgsConstructor
@RestController
public class RoomEscapeController {
    public static final LocalTime BEGIN_TIME = LocalTime.of(11, 0, 0);
    public static final LocalTime LAST_TIME = LocalTime.of(20, 30, 0);

    private final RoomEscapeService roomEscapeService;

    @PostMapping
    public ResponseEntity<Void> reservation(@RequestBody @Valid ReservationRequestDto requestDto) {
        checkOutOfBusinessHours(requestDto.getTime());
        checkUnitOf30Minutes(requestDto.getTime());

        long createdId = roomEscapeService.reservation(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/reservations/" + createdId))
                .build();
    }

    private void checkOutOfBusinessHours(LocalTime time) {
        if (time.isBefore(BEGIN_TIME) || time.isAfter(LAST_TIME)) {
            throw new ReservationException(OUT_OF_BUSINESS_HOURS);
        }
    }

    private void checkUnitOf30Minutes(LocalTime time) {
        if (time.getMinute() % 30 != 0) {
            throw new ReservationException(NOT_UNIT_OF_30_MINUTES);
        }
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDto> findReservationById(@PathVariable long reservationId) {
        ReservationResponseDto responseDto = roomEscapeService.findReservationById(reservationId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long reservationId) {
        roomEscapeService.cancelReservation(reservationId);
        return ResponseEntity.noContent()
                .build();
    }
}
