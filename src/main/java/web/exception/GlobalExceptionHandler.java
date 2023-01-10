package web.exception;

import java.time.DateTimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Void> handleDateTimeException() {
        return ResponseEntity.badRequest().build();
    }
}
