package roomescape.theme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedThemeException extends RuntimeException {
    public DuplicatedThemeException() {
        super("이미 존재하는 테마입니다.");
    }
}
