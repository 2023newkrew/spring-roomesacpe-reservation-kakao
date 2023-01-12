package roomescape.dto;

import roomescape.model.Theme;

public class ThemeRequestDto {
    private String name;
    private String desc;
    private Integer price;

    public ThemeRequestDto() {}

    public ThemeRequestDto(Theme theme) {
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
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
