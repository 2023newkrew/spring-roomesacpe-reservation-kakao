package web.reservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import web.exception.ErrorCode;

@Getter
public class ReservationException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public ReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }
}
