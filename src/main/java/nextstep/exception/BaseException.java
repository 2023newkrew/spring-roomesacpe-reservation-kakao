package nextstep.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties({"stackTrace", "suppressed", "cause", "message", "localizedMessage"})
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "error")
public class BaseException extends RuntimeException {

    protected String message;

    @JsonIgnore
    protected HttpStatus errorStatus;

    public BaseException(String message, HttpStatus errorStatus) {
        this.message = message;
        this.errorStatus = errorStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
