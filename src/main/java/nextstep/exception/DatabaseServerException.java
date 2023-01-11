package nextstep.exception;

import org.springframework.http.HttpStatus;

public class DatabaseServerException extends BaseException {

    public DatabaseServerException(ExceptionMetadata metadata) {
        super(metadata.getMessage(), metadata.getHttpStatus());
    }

    public DatabaseServerException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
