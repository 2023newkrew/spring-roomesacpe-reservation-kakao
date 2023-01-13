package nextstep.exception;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends RoomEscapeException{
    public ReservationNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, id + "번에 해당하는 예약이 없습니다.");
    }
}
