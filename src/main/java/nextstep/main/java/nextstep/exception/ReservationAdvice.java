package nextstep.main.java.nextstep.exception;

import nextstep.main.java.nextstep.exception.error.Error;
import nextstep.main.java.nextstep.exception.error.ErrorResponse;
import nextstep.main.java.nextstep.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.exception.exception.NoSuchReservationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static nextstep.main.java.nextstep.exception.error.ReservationError.DUPLICATE_RESERVATION_ERROR;
import static nextstep.main.java.nextstep.exception.error.ReservationError.NO_SUCH_RESERVATION_ERROR;

@RestControllerAdvice
public class ReservationAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateReservationException e) {
        return handleExceptionInternal(DUPLICATE_RESERVATION_ERROR);
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<?> handleNotExists() {
        return handleExceptionInternal(NO_SUCH_RESERVATION_ERROR);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Error error) {
        return ResponseEntity.status(error.getHttpStatus())
                .body(new ErrorResponse(error.getErrorCode(), error.getErrorMessage()));
    }
    
}
