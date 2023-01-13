package web.theme.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import web.exception.ErrorCode;

@Getter
public class ThemeException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public ThemeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

}
