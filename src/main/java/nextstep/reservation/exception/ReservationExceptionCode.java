package nextstep.reservation.exception;

public enum ReservationExceptionCode {
    DUPLICATE_TIME_RESERVATION("해당 시간에 예약이 이미 존재합니다."),
    NO_SUCH_RESERVATION("예약이 존재하지 않습니다.");

    private final String message;

    ReservationExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
