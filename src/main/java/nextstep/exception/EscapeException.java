package nextstep.exception;

public class EscapeException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    // 에러코드를 명시하지 않은 경우 '서버 내부 오류'를 기본값으로 한다
    public EscapeException() {
        this(ErrorCode.INTERNAL_SERVER_ERROR);
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
