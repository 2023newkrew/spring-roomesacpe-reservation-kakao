package web.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ThemeResponseDTO {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

}
