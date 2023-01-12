package reservation.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ConnectionException extends DBException{

    public ConnectionException(String errorMessage){
        super(errorMessage);
    }
    @Override
    public HttpStatus getHttpStatus(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
