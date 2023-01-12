package nextstep.main.java.nextstep.exception;

import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static nextstep.main.java.nextstep.exception.ErrorCode.DUPLICATE_RESERVATION_ERROR;
import static nextstep.main.java.nextstep.exception.ErrorCode.NO_SUCH_RESERVATION_ERROR;

@RestControllerAdvice
public class ReservationAdvice {
    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<?> handleDuplicateReservationException() {
        return new ResponseEntity<>(DUPLICATE_RESERVATION_ERROR.getErrorMessage(), DUPLICATE_RESERVATION_ERROR.getErrorCode());
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<?> handleNoSuchReservationException() {
        return new ResponseEntity<>(NO_SUCH_RESERVATION_ERROR.getErrorMessage(), NO_SUCH_RESERVATION_ERROR.getErrorCode());
    }
}
