package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ThemeRequestDTO {
    private final String name;

    private final String desc;

    private final int price;
}
