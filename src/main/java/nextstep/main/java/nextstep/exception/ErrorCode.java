package nextstep.main.java.nextstep.exception;

import org.springframework.http.HttpStatus;

import static nextstep.main.java.nextstep.message.ExceptionMessage.*;

public enum ErrorCode {
    DUPLICATE_RESERVATION_ERROR(HttpStatus.CONFLICT, DUPLICATE_RESERVATION_MESSAGE),
    NO_SUCH_RESERVATION_ERROR(HttpStatus.NOT_FOUND, NO_SUCH_RESERVATION_MESSAGE),
    DUPLICATE_THEME_ERROR(HttpStatus.CONFLICT, DUPLICATE_THEME_MESSAGE),
    NO_SUCH_THEME_ERROR(HttpStatus.NOT_FOUND, NO_SUCH_THEME_MESSAGE),
    RELATED_THEME_DELETE_ERROR(HttpStatus.BAD_REQUEST, RELATED_THEME_DELETION_MESSAGE);
    private final HttpStatus errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    }
