package web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;

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
    public ResponseEntity<Void> handleDuplicatedReservationException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(DuplicatedThemeException.class)
    public ResponseEntity<Void> handleDuplicatedThemeException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(NoSuchThemeException.class)
    public ResponseEntity<Void> NoSuchThemeException() {
        return ResponseEntity.notFound().build();
    }

}
