package nextstep.dto.response;

import nextstep.error.ErrorType;

public class ErrorResponse {

    private int status;
    private String message;

    public ErrorResponse(ErrorType error) {
        this.status = error.getHttpStatus();
        this.message = error.getMessage();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
