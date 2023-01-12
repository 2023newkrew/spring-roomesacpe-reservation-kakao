package nextstep.main.java.nextstep.global.exception.error;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int errorCode;
    private final String message;

    public ErrorResponse(int code, String message) {
        this.errorCode = code;
        this.message = message;
    }
}
