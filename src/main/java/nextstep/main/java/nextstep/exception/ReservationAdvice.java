package nextstep.main.java.nextstep.exception;

import nextstep.main.java.nextstep.message.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static nextstep.main.java.nextstep.message.ExceptionMessage.DUPLICATE_RESERVATION_MESSAGE;
import static nextstep.main.java.nextstep.message.ExceptionMessage.NO_SUCH_RESERVATION_MESSAGE;

@RestControllerAdvice
public class ReservationAdvice {
    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<?> handleDuplicate(){
        return new ResponseEntity<>(DUPLICATE_RESERVATION_MESSAGE, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchReservationException.class)
    public ResponseEntity<?> handleNotExists(){
        return new ResponseEntity<>(NO_SUCH_RESERVATION_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
