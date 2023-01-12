package nextstep.reservation.exceptions.exception;

import org.springframework.http.HttpStatus;

public class DuplicateReservationException extends RestAPIException {
    public DuplicateReservationException() {
        this("해당 시간에 중복된 예약이 있습니다.");
    }

    public DuplicateReservationException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
