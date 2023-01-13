package nextstep.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

    public ConflictException(String... errorMessage) {
        super(HttpStatus.CONFLICT, errorMessage);
    }
}
