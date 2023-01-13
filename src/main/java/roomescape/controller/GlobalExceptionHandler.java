package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.DuplicateReservationScheduleException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.ThemeNotFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateReservationScheduleException.class)
    public ResponseEntity DuplicateReservationScheduleException(DuplicateReservationScheduleException e) {
        return ResponseEntity.unprocessableEntity().body("이미 예약된 시간입니다.");
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    public ResponseEntity ThemeNotFoundException(ThemeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없는 테마입니다.");
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity ReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없는 예약입니다.");
    }

}
