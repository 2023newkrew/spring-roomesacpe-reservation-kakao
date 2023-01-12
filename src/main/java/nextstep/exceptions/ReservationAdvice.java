package nextstep.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationAdvice {
    @ExceptionHandler(ReservationException.class)
    public ResponseEntity handle() {
        return ResponseEntity.badRequest().body("ReservationException");
    }
}
