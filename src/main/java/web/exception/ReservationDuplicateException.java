package web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReservationDuplicateException extends RuntimeException {
    public ReservationDuplicateException() {
        super("해당 시간은 이미 예약되었습니다.");
    }
}
