package roomescape.theme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedThemeException extends RuntimeException {
    public DuplicatedThemeException() {
    }

    public DuplicatedThemeException(String message) {
        super(message);
    }
}
