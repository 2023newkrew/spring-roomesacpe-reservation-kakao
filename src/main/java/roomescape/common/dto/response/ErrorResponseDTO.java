package roomescape.common.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseDTO {

    private final String message;

    public static ErrorResponseDTO from(String message) {
        return new ErrorResponseDTO(message);
    }
}
