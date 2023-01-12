package nextstep.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(ExceptionMetadata metadata) {
        super(metadata.getMessage(), metadata.getHttpStatus());
    }

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
