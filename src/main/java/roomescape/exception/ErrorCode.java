package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, "해당 항목이 존재하지 않습니다."),
    THEME_NAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 테마 이름이 이미 존재합니다."),
    RESERVATION_DATETIME_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 예약 날짜 또는 시간이 이미 존재합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus statusCode;
    private final String message;

    ErrorCode(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return this.message;
    }
}
