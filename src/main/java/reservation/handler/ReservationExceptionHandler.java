package reservation.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reservation.handler.exception.DuplicatedObjectException;
import reservation.handler.exception.ObjectNotFoundException;

@RestControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(DuplicatedObjectException.class)
    public ResponseEntity<?> handleDuplicatedReservationException(DuplicatedObjectException e) {
        return ResponseEntity.badRequest().body(e.toString());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> handleReservationNotFoundException(ObjectNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleDefaultException(Exception e) {
        return ResponseEntity.internalServerError().body(e.toString());
    }
}
