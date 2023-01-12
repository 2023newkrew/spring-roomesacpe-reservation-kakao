package reservation.util.exception.restAPI;

import org.springframework.http.HttpStatus;

public class ExistException extends RestAPIException {

    public ExistException(String errorMessage){
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus(){
        return HttpStatus.BAD_REQUEST;
    }
}
