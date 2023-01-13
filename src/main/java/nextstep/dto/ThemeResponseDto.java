package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeResponseDto {

    private final Long id;

    private final String name;

    private final String description;

    private final Integer price;
}
