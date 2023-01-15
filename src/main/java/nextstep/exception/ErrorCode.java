package nextstep.exception;

public enum ErrorCode {
    RESERVATION_NOT_FOUND("예약 내역을 찾을 수 없습니다."),
    THEME_NOT_FOUND("테마를 찾을 수 없습니다."),
    DUPLICATED_RESERVATION_EXISTS("이미 예약된 일시에는 예약이 불가능합니다."),
    DUPLICATED_THEME_EXISTS("이미 해당 이름을 가진 테마가 존재합니다."),
    SQL_ERROR("SQLException이 발생했습니다."),
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
    ;

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
