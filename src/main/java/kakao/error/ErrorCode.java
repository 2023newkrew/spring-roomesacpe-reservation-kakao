package kakao.error;

public enum ErrorCode {
    DUPLICATE_RESERVATION(400, "해당 시간에 예약이 존재합니다."),
    DUPLICATE_THEME(409, "해당 이름의 테마가 존재합니다."),
    RESERVATION_NOT_FOUND(400, "해당 ID의 예약이 존재하지 않습니다."),
    THEME_NOT_FOUND(404, "해당 ID의 테마가 존재하지 않습니다"),
    THEME_CONFLICT(409, "사용중인 테마입니다"),
    ILLEGAL_DATE(400, "날짜는 오늘보다 이후여야 합니다");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
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
