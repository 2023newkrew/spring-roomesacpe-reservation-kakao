package nextstep.reservation.exceptions.exception;

import org.springframework.http.HttpStatus;

public class DuplicateReservationNameException extends RestAPIException{

    public DuplicateReservationNameException() {
        this("예약이름은 중복될 수 있습니다.");
    }

    public DuplicateReservationNameException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
