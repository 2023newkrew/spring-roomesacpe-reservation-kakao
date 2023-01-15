package nextstep.reservation.exceptions.exception;

import org.springframework.http.HttpStatus;

public class DoesNotCreateDataException extends RestAPIException {

    public DoesNotCreateDataException() {
        this("데이터를 생성하는데 실패하였습니다.");
    }

    public DoesNotCreateDataException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
