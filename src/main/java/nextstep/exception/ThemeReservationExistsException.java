package nextstep.exception;

import org.springframework.http.HttpStatus;

public class ThemeReservationExistsException extends RoomEscapeException{
    public ThemeReservationExistsException(Long id) {
        super(HttpStatus.CONFLICT, id + "번의 테마 내 예약이 존재합니다.");
    }
}
