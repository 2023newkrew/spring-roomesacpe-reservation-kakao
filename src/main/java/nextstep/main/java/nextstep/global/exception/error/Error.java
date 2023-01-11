package nextstep.main.java.nextstep.global.exception.error;

import org.springframework.http.HttpStatus;

public interface Error {
    int getErrorCode();
    String getErrorMessage();
    HttpStatus getHttpStatus();
}
