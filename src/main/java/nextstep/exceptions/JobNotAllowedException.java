package nextstep.exceptions;

public class JobNotAllowedException extends RuntimeException {
    public JobNotAllowedException(String message) {
        super(message);
    }
}
