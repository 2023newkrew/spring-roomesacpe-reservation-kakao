package nextstep.reservation.exceptions.exception;

import org.springframework.http.HttpStatus;

public class DuplicateThemeNameException extends RestAPIException {

    public DuplicateThemeNameException() {
        this("중복된 이름으로 테마를 생성할 수 없습니다.");
    }

    public DuplicateThemeNameException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
