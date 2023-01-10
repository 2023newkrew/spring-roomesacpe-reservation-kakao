package nextstep.main.java.nextstep.exception.error;

import org.springframework.http.HttpStatus;

public interface Error {
    int getErrorCode();
    String getErrorMessage();
    HttpStatus getHttpStatus();
}
