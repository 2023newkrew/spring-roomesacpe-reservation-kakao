package web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.ReservationRequestDto;
import web.dto.ReservationResponseDto;
import web.service.RoomEscapeService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;

@RequiredArgsConstructor
@RestController
public class RoomEscapeController {
    private static final LocalTime BEGIN_TIME = LocalTime.of(11, 0, 0);
    private static final LocalTime LAST_TIME = LocalTime.of(20, 30, 0);

    private final RoomEscapeService roomEscapeService;

    @PostMapping("/reservations")
    public ResponseEntity<Void> reservation(@RequestBody @Valid ReservationRequestDto requestDto) {
        if (isInvalidTime(requestDto.getTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        long createdId = roomEscapeService.reservation(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/reservations/" + createdId))
                .build();
    }

    private boolean isInvalidTime(LocalTime reservationTime) {
        return isOutOfBusinessHours(reservationTime) || isUnitOf30Minutes(reservationTime);
    }

    private boolean isOutOfBusinessHours(LocalTime time) {
        return time.isBefore(BEGIN_TIME) || time.isAfter(LAST_TIME);
    }

    private boolean isUnitOf30Minutes(LocalTime time) {
        return time.getMinute() % 30 != 0;
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponseDto> findReservationById(@PathVariable long reservationId) {
        ReservationResponseDto responseDto = roomEscapeService.findReservationById(reservationId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long reservationId) {
        roomEscapeService.cancelReservation(reservationId);
        return ResponseEntity.noContent()
                .build();
    }
}
