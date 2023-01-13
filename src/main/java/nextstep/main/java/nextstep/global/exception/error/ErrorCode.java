package nextstep.main.java.nextstep.global.exception.error;

import org.springframework.http.HttpStatus;

import static nextstep.main.java.nextstep.global.constant.ExceptionMessage.*;

public enum ErrorCode implements Error {
    NO_SUCH_RESERVATION_ERROR(404, HttpStatus.NOT_FOUND, NO_SUCH_RESERVATION_MESSAGE),
    NO_SUCH_THEME_ERROR(404, HttpStatus.NOT_FOUND, NO_SUCH_THEME_MESSAGE),
    DUPLICATE_RESERVATION_ERROR(409, HttpStatus.CONFLICT, DUPLICATE_RESERVATION_MESSAGE),
    ALREADY_RESERVED_THEME_ERROR(409, HttpStatus.CONFLICT, ALREADY_RESERVED_THEME_MESSAGE),
    NOT_SUPPORTED_OPERATION_ERROR(501, HttpStatus.NOT_IMPLEMENTED, NOT_SUPPORTED_OPERATIONS_MESSAGE);


    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
