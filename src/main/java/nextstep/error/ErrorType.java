package nextstep.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    INVALID_RESERVATION_REQUEST_DATA(HttpStatus.BAD_REQUEST.value(), "예약 생성 시에는 날짜, 시간, 이름을 모두 기입해야 합니다."),
    DUPLICATE_RESERVATION(HttpStatus.BAD_REQUEST.value(), "같은 날짜와 시간에 해당하는 예약이 존재합니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당하는 예약을 찾을 수 없습니다."),
    INVALID_THEME_REQUEST_DATA(HttpStatus.BAD_REQUEST.value(), "테마 생성 시에는 이름, 설명, 가격을 모두 기입해야 합니다."),
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당하는 테마를 찾을 수 없습니다."),
    DUPLICATE_THEME(HttpStatus.BAD_REQUEST.value(), "동일한 이름을 가진 테마가 존재합니다."),
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST.value(), "올바르지 않은 요청 파라미터입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예기치 못한 오류로 해당 요청을 처리하지 못하였습니다."),
    ;

    private int httpStatus;
    private String message;

    ErrorType(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return this.name();
    }

}
