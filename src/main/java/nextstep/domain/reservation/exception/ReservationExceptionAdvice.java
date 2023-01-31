package nextstep.domain.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionAdvice {

    @ExceptionHandler(DuplicatedDateAndTimeException.class)
    public ResponseEntity<String> handleDuplicatedDateAndTimeException() {
        return ResponseEntity.badRequest().body("이미 예약된 날짜와 시간입니다.");
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException() {
        return ResponseEntity.badRequest().body("존재하지 않는 예약입니다.");
    }
}
