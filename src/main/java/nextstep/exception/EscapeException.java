package nextstep.exception;

public class EscapeException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public EscapeException() {
    }

    public EscapeException(ErrorCode errorCode) {
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
