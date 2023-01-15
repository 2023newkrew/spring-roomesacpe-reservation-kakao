package nextstep.reservation.exceptions.exception;

import org.springframework.http.HttpStatus;

public class DuplicateReservationTimeException extends RestAPIException {
    public DuplicateReservationTimeException() {
        this("해당 시간에 중복된 예약이 있습니다.");
    }

    public DuplicateReservationTimeException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
