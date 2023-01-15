package nextstep.reservation.exception;

import nextstep.exception.ErrorCode;

public enum ThemeReservationErrorCode implements ErrorCode {
    RESERVATION_NOT_FOUND("존재하지 않는 예약압니다."),
    CANNOT_RESERVE_FOR_SAME_DATETIME("같은 날짜/시간에 예약할 수 없습니다.");

    private final String message;
    ThemeReservationErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
