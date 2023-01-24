package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedReservationException extends RuntimeException {
    public DuplicatedReservationException() {
    }

    public DuplicatedReservationException(String message) {
        super(message);
    }
}