package roomescape.dto;

import roomescape.model.Theme;

public class ThemeRequestDto {
    private String name;
    private String desc;
    private Integer price;

    public ThemeRequestDto() {}

    public ThemeRequestDto(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toEntity() {
        return new Theme(null, name, desc, price);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getPrice() {
        return price;
    }
}
