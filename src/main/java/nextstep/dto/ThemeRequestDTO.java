package nextstep.dto;

import lombok.Data;

@Data
public class ThemeRequestDTO {
    private final String name;

    private final String desc;

    private final int price;
}
