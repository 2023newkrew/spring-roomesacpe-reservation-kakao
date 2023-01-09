package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.dto.ReservationRequestDto;
import web.exception.ReservationDuplicateException;
import web.service.RoomEscapeService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;

@RestController
public class RoomEscapeController {
    private static final LocalTime BEGIN_TIME = LocalTime.of(11, 0, 0);
    private static final LocalTime LAST_TIME = LocalTime.of(20, 30, 0);

    private final RoomEscapeService roomEscapeService;

    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @ExceptionHandler(ReservationDuplicateException.class)
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
        if (isOutOfBusinessHours(reservationTime)) {
            return true;
        }
        if (isUnitOf30Minutes(reservationTime)) {
            return true;
        }
        return false;
    }

    private boolean isOutOfBusinessHours(LocalTime time) {
        return time.isBefore(BEGIN_TIME) || time.isAfter(LAST_TIME);
    }

    private boolean isUnitOf30Minutes(LocalTime time) {
        return time.getMinute() % 30 != 0;
    }
}
