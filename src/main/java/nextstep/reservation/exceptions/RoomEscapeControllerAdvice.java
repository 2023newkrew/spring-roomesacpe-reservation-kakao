package nextstep.reservation.exceptions;

import nextstep.reservation.controller.ReservationController;
import nextstep.reservation.controller.ThemeController;
import nextstep.reservation.exceptions.exception.RestAPIException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {ReservationController.class, ThemeController.class})
public class RoomEscapeControllerAdvice {
    @ExceptionHandler(RestAPIException.class)
    public ResponseEntity<String> handleDuplicatedReservation(RestAPIException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(e.getMessage());
    }
}
