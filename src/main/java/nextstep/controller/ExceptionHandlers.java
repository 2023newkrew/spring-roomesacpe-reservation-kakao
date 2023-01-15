package nextstep.controller;

import nextstep.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(DuplicateTimeReservationException.class)
    public ResponseEntity handleDuplicateTimeReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalReservationTimeException.class)
    public ResponseEntity handleIllegalReservationTimeException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoReservationException.class)
    public ResponseEntity handleNoReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DeleteReservationFailureException.class)
    public ResponseEntity handleIDeleteReservationFailureException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NegativeThemePriceException.class)
    public ResponseEntity handleNegativeThemePriceException() {
        return ResponseEntity.badRequest().build();
    }
}
