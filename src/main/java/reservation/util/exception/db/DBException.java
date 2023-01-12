package reservation.util.exception.db;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class DBException extends RuntimeException{

    @Getter
    private final String errorMessage;

    public abstract HttpStatus getHttpStatus();

    public DBException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
