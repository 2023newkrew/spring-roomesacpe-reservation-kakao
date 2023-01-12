package reservation.util.exception.restAPI;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestAPIException {


    public NotFoundException(String errorMessage){
        super(errorMessage);
    }
    @Override
    public HttpStatus getHttpStatus(){
        return HttpStatus.NOT_FOUND;
    }
}
