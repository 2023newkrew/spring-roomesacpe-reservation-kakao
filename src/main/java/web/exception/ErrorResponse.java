package web.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private final String message;

    public static ErrorResponse from(String message) {
        return new ErrorResponse(message);
    }
}
