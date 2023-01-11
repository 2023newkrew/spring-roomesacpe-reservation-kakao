package roomservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomservice.exceptions.exception.DuplicatedReservationException;
import roomservice.exceptions.exception.NonExistentReservationException;

/**
 * ReservationAdvice handles exceptions created by reservation operations.
 */
@ControllerAdvice
public class ReservationAdvice {
    /**
     * Handles {@link roomservice.exceptions.exception.DuplicatedReservationException} on whole program.
     * @return "bad request" with some message.
     */
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity handleDuplicatedReservationException() {
        return ResponseEntity.badRequest().body("이미 예약된 시간입니다.");
    }

    /**
     * Handles {@link roomservice.exceptions.exception.NonExistentReservationException} on whole program.
     * @return "bad request" with some message.
     */
    @ExceptionHandler(NonExistentReservationException.class)
    public ResponseEntity<String> handleNonExistentReservationException() {
        return ResponseEntity.badRequest().body("존재하지 않는 예약 id입니다.");
    }
}
