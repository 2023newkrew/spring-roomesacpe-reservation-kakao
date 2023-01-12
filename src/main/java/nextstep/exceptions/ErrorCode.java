package nextstep.exceptions;

public enum ErrorCode {
    ALREADY_RESERVATION_EXISTS("이미 해당 시간에 예약이 존재합니다."),
    RESERVATION_NOT_FOUND("해당 예약이 존재하지 않습니다.");
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
