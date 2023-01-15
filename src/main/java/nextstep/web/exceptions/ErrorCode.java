package nextstep.web.exceptions;

public enum ErrorCode {
    ALREADY_RESERVATION_EXISTS("이미 해당 시간에 예약이 존재합니다."),
    RESERVATION_NOT_FOUND("해당 예약이 존재하지 않습니다."),
    ALREADY_THEME_EXISTS("이미 동일한 이름의 테마가 존재합니다."),
    THEME_NOT_FOUND("해당 테마가 존재하지 않습니다."),
    RESERVATION_WITH_THIS_THEME_EXISTS("해당 테마를 가진 예약이 존재하여 삭제할 수 없습니다.");
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
