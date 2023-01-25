package roomescape.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchThemeException extends RuntimeException {
    public NoSuchThemeException(String message) {
        super(message);
    }
}
