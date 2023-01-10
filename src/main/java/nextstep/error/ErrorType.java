package nextstep.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    INVALID_RESERVATION_REQUEST_DATA(HttpStatus.BAD_REQUEST.value(), "예약 생성 시에는 날짜, 시간, 이름을 모두 기입해야 합니다."),
    DUPLICATE_RESERVATION(HttpStatus.BAD_REQUEST.value(), "같은 날짜와 시간에 해당하는 예약이 존재합니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당하는 예약을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예기치 못한 오류로 해당 요청을 처리하지 못하였습니다."),
    ;

    private int status;
    private String message;

    ErrorType(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
