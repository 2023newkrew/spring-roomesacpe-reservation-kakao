package roomescape.theme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoThemeException extends RuntimeException {
    public NoThemeException() {
        super("테마 목록이 비어있습니다.");
    }
}
