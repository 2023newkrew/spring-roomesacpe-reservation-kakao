package web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReservationDuplicateException extends RuntimeException {
    public ReservationDuplicateException() {
        super("요청하신 시간대에 이미 예약이 있습니다.");
    }
}
