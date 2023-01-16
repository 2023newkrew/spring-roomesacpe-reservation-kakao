package nextstep.main.java.nextstep.global.exception.exception;

import nextstep.main.java.nextstep.global.exception.error.ErrorCode;

public class NotSupportedOperationException extends ApiException{
    public NotSupportedOperationException() {
        super();
    }

    public NotSupportedOperationException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getError() {
        return ErrorCode.NOT_SUPPORTED_OPERATION_ERROR;
    }
}
