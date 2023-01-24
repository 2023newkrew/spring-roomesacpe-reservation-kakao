package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTimeReservationException extends RuntimeException {
    public InvalidTimeReservationException() {
    }

    public InvalidTimeReservationException(String message) {
        super(message);
    }
}
