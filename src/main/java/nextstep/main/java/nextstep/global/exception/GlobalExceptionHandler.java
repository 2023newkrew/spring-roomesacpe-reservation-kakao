package nextstep.main.java.nextstep.global.exception;

import nextstep.main.java.nextstep.global.exception.error.Error;
import nextstep.main.java.nextstep.global.exception.error.ErrorResponse;
import nextstep.main.java.nextstep.global.exception.exception.DuplicateReservationException;
import nextstep.main.java.nextstep.global.exception.exception.NoSuchReservationException;
import nextstep.main.java.nextstep.global.exception.error.ReservationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateReservationException e) {
        return handleExceptionInternal(ReservationError.DUPLICATE_RESERVATION_ERROR);
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<?> handleNotExists() {
        return handleExceptionInternal(ReservationError.NO_SUCH_RESERVATION_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.internalServerError().build();
    }

    protected ResponseEntity<Object> handleExceptionInternal(Error error) {
        return ResponseEntity.status(error.getHttpStatus())
                .body(new ErrorResponse(error.getErrorCode(), error.getErrorMessage()));
    }
}
