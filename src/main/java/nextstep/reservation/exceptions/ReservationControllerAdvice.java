package nextstep.reservation.exceptions;

import nextstep.reservation.controller.ReservationController;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {ReservationController.class})
public class ReservationControllerAdvice {
    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<String> DuplicateReservation() {
        return ResponseEntity.badRequest().body("해당 시간에 중복된 예약이 있습니다.");
    }

}
