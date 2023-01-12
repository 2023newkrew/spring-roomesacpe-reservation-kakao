package reservation.util.exception.restAPI;

import org.springframework.http.HttpStatus;

public class DuplicateException extends RestAPIException{

    public DuplicateException(String errorMessage){
        super(errorMessage);
    }

    @Override
    public HttpStatus getHttpStatus(){
        return HttpStatus.BAD_REQUEST;
    }
}
