package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ThemeDTO {
    private final Long id;

    private final String name;

    private final String desc;

    private final Integer price;
}
