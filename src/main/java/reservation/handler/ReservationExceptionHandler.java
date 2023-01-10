package reservation.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reservation.handler.exception.DuplicatedReservationException;
import reservation.handler.exception.ReservationNotFoundException;

@RestControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity<?> handleDuplicatedReservationException(DuplicatedReservationException e) {
        return ResponseEntity.badRequest().body(e.toString());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<?> handleReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleDefaultException(Exception e) {
        return ResponseEntity.internalServerError().body(e.toString());
    }
}
