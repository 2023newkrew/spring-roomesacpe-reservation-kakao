package nextstep.exception;

import org.springframework.http.HttpStatus;

public class ThemeNotFoundException extends RoomEscapeException{
    public ThemeNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, id + "번에 해당하는 테마가 없습니다.");
    }
}
