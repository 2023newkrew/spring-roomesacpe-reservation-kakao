package nextstep.etc.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class BaseException extends RuntimeException {

    @Getter
    private final String className;

    @Getter
    private final HttpStatus httpStatus;


    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());

        this.className = getClass().getSimpleName();
        this.httpStatus = errorMessage.getHttpStatus();
    }

    public BaseException(Throwable throwable) {
        super(throwable.getMessage());

        this.className = throwable.getClass()
                .getSimpleName();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}