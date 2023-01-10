package nextstep.main.java.nextstep.exception.error;

import org.springframework.http.HttpStatus;

import static nextstep.main.java.nextstep.exception.exception.ExceptionMessage.DUPLICATE_RESERVATION_MESSAGE;
import static nextstep.main.java.nextstep.exception.exception.ExceptionMessage.NO_SUCH_RESERVATION_MESSAGE;

public enum ReservationError implements Error {
    NO_SUCH_RESERVATION_ERROR(404, HttpStatus.NOT_FOUND, NO_SUCH_RESERVATION_MESSAGE),
    DUPLICATE_RESERVATION_ERROR(409, HttpStatus.CONFLICT, DUPLICATE_RESERVATION_MESSAGE);

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ReservationError(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
