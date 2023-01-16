package kakao.error;

import kakao.error.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Custom
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedReservationException(DuplicatedReservationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(DuplicatedThemeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedThemeException(DuplicatedThemeException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(UsingThemeException.class)
    public ResponseEntity<ErrorResponse> handleUsingThemeException(UsingThemeException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(IllegalCreateReservationRequestException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalCreateReservationRequestException e) {
        return getResponseEntity(e);
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(CustomRuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
