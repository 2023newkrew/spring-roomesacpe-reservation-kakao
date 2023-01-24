package roomescape.theme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoThemeException extends RuntimeException {
    public NoThemeException() {
    }

    public NoThemeException(String message) {
        super(message);
    }
}
