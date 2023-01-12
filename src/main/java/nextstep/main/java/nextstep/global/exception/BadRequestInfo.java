package nextstep.main.java.nextstep.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadRequestInfo {
    String field;
    String message;

}
