package nextstep.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

    public ConflictException(ExceptionMetadata metadata) {
        super(metadata.getMessage(), metadata.getHttpStatus());
    }

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
