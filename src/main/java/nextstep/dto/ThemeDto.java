package nextstep.dto;

import nextstep.domain.Theme;

public class ThemeDto {

    private final String name;
    private final String desc;
    private final Integer price;

    public ThemeDto(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toTheme() {
        return new Theme(name, desc, price);
    }
}
