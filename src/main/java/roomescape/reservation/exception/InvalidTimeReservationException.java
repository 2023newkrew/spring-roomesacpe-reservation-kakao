package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTimeReservationException extends RuntimeException {
    public InvalidTimeReservationException() {
        super("유효하지 않은 시간입니다.");
    }
}
