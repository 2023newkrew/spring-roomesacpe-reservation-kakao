package nextstep.exception;

public class ConstraintViolationException extends RuntimeException{
    public ConstraintViolationException(ErrorCode errorCode){
        super(errorCode.getMessage());
    }
}
