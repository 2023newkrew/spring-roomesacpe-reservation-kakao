package nextstep.main.java.nextstep.exception;

import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.exception.DuplicateThemeException;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchThemeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static nextstep.main.java.nextstep.exception.ErrorCode.*;

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

    @ExceptionHandler(DuplicateThemeException.class)
    public ResponseEntity<?> handleDuplicateThemeException() {
        return new ResponseEntity<>(DUPLICATE_THEME_ERROR.getErrorMessage(), DUPLICATE_THEME_ERROR.getErrorCode());
    }

    @ExceptionHandler(NoSuchThemeException.class)
    public ResponseEntity<?> handleNoSuchThemeException() {
        return new ResponseEntity<>(NO_SUCH_THEME_ERROR.getErrorMessage(), NO_SUCH_THEME_ERROR.getErrorCode());
    }
}
