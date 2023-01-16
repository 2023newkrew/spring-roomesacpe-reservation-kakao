package nextstep.exception;

import org.springframework.http.HttpStatus;

public class ThemeDuplicateException extends RoomEscapeException{
    public ThemeDuplicateException() {
        super(HttpStatus.CONFLICT, "중복된 테마가 존재합니다.");
    }
}
