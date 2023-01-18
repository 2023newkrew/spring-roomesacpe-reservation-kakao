package nextstep.reservation.exceptions.exception;

import org.springframework.http.HttpStatus;

public class NotFoundObjectException extends RestAPIException {

    public NotFoundObjectException() {
        this("해당 객체를 찾을 수 없습니다. 아이디를 확인해주세요.");
    }

    public NotFoundObjectException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
