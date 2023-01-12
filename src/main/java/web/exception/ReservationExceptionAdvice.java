package web.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionAdvice {

    @ExceptionHandler(ReservationDuplicateException.class)
    public ResponseEntity<String> handleReservationDuplicateException(ReservationDuplicateException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(e.getMessage());
    }
}
