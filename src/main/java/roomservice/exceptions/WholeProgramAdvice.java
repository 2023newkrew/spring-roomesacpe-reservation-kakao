package roomservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomservice.exceptions.exception.DuplicatedReservationException;
import roomservice.exceptions.exception.InvalidReservationTimeException;
import roomservice.exceptions.exception.InvalidThemeIdException;

/**
 * ReservationAdvice handles exceptions created by reservation operations.
 */
@ControllerAdvice
public class WholeProgramAdvice {
    /**
     * Handles {@link roomservice.exceptions.exception.DuplicatedReservationException} on whole program.
     * @return "bad request" with some message.
     */
    @ExceptionHandler(DuplicatedReservationException.class)
    public ResponseEntity handleDuplicatedReservationException() {
        return ResponseEntity.badRequest().body("이미 예약된 시간입니다.");
    }

    /**
     * Handles {@link org.springframework.web.bind.MethodArgumentNotValidException} on whole program.
     * @return "bad request" with some message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(){
        return ResponseEntity.badRequest().body("올바른 값을 입력하세요.");
    }

    /**
     * Handles {@link roomservice.exceptions.exception.InvalidReservationTimeException} on whole program.
     * @return "bad request" with some message.
     */
    @ExceptionHandler(InvalidReservationTimeException.class)
    public ResponseEntity handleInvalidReservationTimeException(){
        return ResponseEntity.badRequest().body("시간표 내의 시각을 입력해 주세요.");
    }

    /**
     * Handles {@link roomservice.exceptions.exception.InvalidReservationTimeException} on whole program.
     * @return "bad request" with some message.
     */
    @ExceptionHandler(InvalidThemeIdException.class)
    public ResponseEntity handleInvalidIdThemeException(){
        return ResponseEntity.badRequest().body("올바른 테마 id를 입력해 주세요.");
    }
}
