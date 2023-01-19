package nextstep.controller;

import nextstep.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RoomEscapeControllerAdvice {

    @ExceptionHandler(value = {
            DuplicateReservationException.class,
            ReservationNotFoundException.class,
            ThemeNotFoundException.class,
            ThemeReservedException.class,
            InvalidRequestException.class})
    public ResponseEntity<String> handleBadRequest(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
