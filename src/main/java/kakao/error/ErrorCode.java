package kakao.error;

public enum ErrorCode {
    // 공통 에러
    API_NOT_FOUND(404, "잘못된 API 요청입니다."),

    // 예약 에러
    DUPLICATE_RESERVATION(400, "해당 시간에 예약이 존재합니다."),
    RESERVATION_NOT_FOUND(400, "해당 ID의 예약이 존재하지 않습니다.");

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
