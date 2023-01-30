package roomescape.reservation.exception;

public enum ErrorCode {
    DUPLICATE_RESERVATION(422, "이미 예약된 시간입니다"),
    NOT_FOUND_RESERVATION(404, "존재하지 않는 예약입니다"),
    DUPLICATE_THEME(422, "이미 존재하는 테마 이름 입니다"),
    NOT_FOUND_THEME(404, "존재하지 않는 테마 입니다");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
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
