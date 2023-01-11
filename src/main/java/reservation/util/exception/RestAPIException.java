package reservation.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class RestAPIException extends RuntimeException{
    @Getter
    private final String errorMessage;

    public abstract HttpStatus getHttpStatus();

    public RestAPIException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
