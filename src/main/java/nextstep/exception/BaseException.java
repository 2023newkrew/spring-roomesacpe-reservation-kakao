package nextstep.exception;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties({"stackTrace", "suppressed", "cause", "message", "localizedMessage"})
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "error")
public class BaseException extends RuntimeException {

    protected List<String> errorMessages = new ArrayList<>();

    @JsonIgnore
    protected HttpStatus errorStatus;

    public BaseException(HttpStatus errorStatus, List<String> errorMessages) {
        this.errorStatus = errorStatus;
        this.errorMessages = errorMessages;
    }

    public BaseException(HttpStatus errorStatus, String... errorMessage) {
        this.errorStatus = errorStatus;
        errorMessages.addAll(Arrays.asList(errorMessage));
    }

    @JsonGetter("messages")
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }

}
