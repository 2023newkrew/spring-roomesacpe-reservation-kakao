package nextstep.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(String... errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }
}
