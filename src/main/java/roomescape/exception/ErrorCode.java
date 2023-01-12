package roomescape.exception;

public enum ErrorCode {
    NO_SUCH_ELEMENT("해당 항목이 존재하지 않습니다."),
    THEME_NAME_ALREADY_EXISTS("해당 테마 이름이 이미 존재합니다."),
    RESERVATION_DATETIME_ALREADY_EXISTS("해당 예약 날짜 또는 시간이 이미 존재합니다."),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
