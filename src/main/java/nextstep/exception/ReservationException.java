package nextstep.exception;

public class ReservationException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public ReservationException() {
    }

    public ReservationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
