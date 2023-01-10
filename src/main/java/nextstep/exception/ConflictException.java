package nextstep.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

    public ConflictException(List<String> errorMessages) {
        super(HttpStatus.CONFLICT, errorMessages);
    }

    public ConflictException(String... errorMessage) {
        super(HttpStatus.CONFLICT, errorMessage);
    }
}
