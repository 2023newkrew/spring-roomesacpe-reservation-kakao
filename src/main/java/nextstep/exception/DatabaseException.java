package nextstep.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(Throwable cause) {
        this("데이터베이스에 문제가 발생했습니다.", cause);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
