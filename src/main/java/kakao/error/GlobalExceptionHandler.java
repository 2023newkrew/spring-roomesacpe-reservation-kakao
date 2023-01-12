package kakao.error;

import kakao.error.exception.DuplicatedReservationException;
import kakao.error.exception.DuplicatedThemeException;
import kakao.error.exception.RecordNotFoundException;
import kakao.error.exception.UsingThemeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Custom
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedReservationException(DuplicatedReservationException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(DuplicatedThemeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedThemeException(DuplicatedThemeException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(UsingThemeException.class)
    public ResponseEntity<ErrorResponse> handleUsingThemeException(UsingThemeException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
