package roomescape.theme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.entity.Theme;

public class ThemeRequestDto {
    @JsonFormat
    private String name;
    @JsonFormat
    private String desc;
    @JsonFormat
    private Integer price;

    public ThemeRequestDto(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toEntity() {
        return Theme.builder().name(name).desc(desc).price(price).build();
    }
}
