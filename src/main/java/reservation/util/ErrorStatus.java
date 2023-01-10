package reservation.util;

public enum ErrorStatus {
    RESERVATION_DUPLICATED(400, "해당 날짜와 시간에 이미 예약이 존재합니다."),
    RESERVATION_NOT_FOUND(400, "해당 예약이 존재하지 않습니다.");


    private final int status;
    private final String message;


    ErrorStatus(int status, String message) {
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
