package nextstep.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(ErrorCode errorCode){
        super(errorCode.getMessage());
    }
}
