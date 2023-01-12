package web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReservationNotFoundException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public ReservationNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }
}