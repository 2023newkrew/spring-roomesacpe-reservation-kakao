package reservation.util.exception;

import org.springframework.http.HttpStatus;

public class QueryException extends DBException{

    public QueryException(String errorMessage){
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
