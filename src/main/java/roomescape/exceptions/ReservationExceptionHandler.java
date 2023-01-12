package roomescape.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exceptions.exception.DuplicatedReservationException;
import roomescape.exceptions.exception.NoSuchReservationException;

@ControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity handleDuplicatedReservationException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 예약된 시간입니다.");
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<String> handleNonExistentReservationException() {
        return ResponseEntity.badRequest().body("존재하지 않는 예약 id입니다.");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException() {
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }
}
