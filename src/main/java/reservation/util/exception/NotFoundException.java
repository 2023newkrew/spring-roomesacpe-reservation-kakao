package reservation.util.exception;

import reservation.util.ErrorStatus;

public class NotFoundException extends RuntimeException {

    private ErrorStatus errorStatus;

    public NotFoundException(ErrorStatus errorStatus){
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
