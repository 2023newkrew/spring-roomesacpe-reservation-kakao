package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity DuplicateReservationScheduleException(IllegalArgumentException e) {
        return ResponseEntity.unprocessableEntity().body("이미 예약된 시간입니다.");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity ReservationNotFoundException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("잘못된 접근입니다.");
    }

}
