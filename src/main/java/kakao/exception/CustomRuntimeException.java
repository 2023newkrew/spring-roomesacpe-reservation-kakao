package kakao.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class CustomRuntimeException extends RuntimeException {
    ErrorCode errorCode;
}
