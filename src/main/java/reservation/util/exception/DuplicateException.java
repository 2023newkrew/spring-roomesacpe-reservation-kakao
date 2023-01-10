package reservation.util.exception;

import reservation.util.ErrorStatus;

public class DuplicateException extends RuntimeException{

    private ErrorStatus errorStatus;

    public DuplicateException(ErrorStatus errorStatus){
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
