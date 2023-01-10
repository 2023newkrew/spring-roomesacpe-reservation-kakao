package web.exception;

import java.time.DateTimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Void> handleDateTimeException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<Void> handleNoSucReservationException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity<Void> handleDuplicatedReservationExcetion() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
