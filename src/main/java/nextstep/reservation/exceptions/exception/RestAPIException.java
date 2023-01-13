package nextstep.reservation.exceptions.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class RestAPIException extends RuntimeException {
    @Getter
    private String responseMessage;

    public abstract HttpStatus getHttpStatus();
    public RestAPIException(String responseMessage) {
        super(responseMessage);
    }

}
