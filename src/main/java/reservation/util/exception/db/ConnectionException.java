package reservation.util.exception.db;

import org.springframework.http.HttpStatus;
import reservation.util.exception.db.DBException;

public class ConnectionException extends DBException {

    public ConnectionException(String errorMessage){
        super(errorMessage);
    }
    @Override
    public HttpStatus getHttpStatus(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
