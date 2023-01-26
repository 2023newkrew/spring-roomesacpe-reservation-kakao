package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedReservationException extends RuntimeException {
    public DuplicatedReservationException() {
        super("같은 날짜/시간에 이미 예약이 있습니다.");
    }
}