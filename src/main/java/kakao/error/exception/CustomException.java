package kakao.error.exception;

import kakao.error.ErrorCode;

public abstract class CustomException extends RuntimeException{

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public abstract ErrorCode getErrorCode();
}
