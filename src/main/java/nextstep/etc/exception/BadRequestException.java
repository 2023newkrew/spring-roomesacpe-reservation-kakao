package nextstep.etc.exception;

public class BadRequestException extends BaseException {
    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public BadRequestException(Throwable throwable) {
        super(throwable);
    }
}
