package roomescape.theme.domain;

import roomescape.theme.dto.ThemeDto;

public class Theme {

    private Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(ThemeDto themeDto) {
        this.name = themeDto.getName();
        this.desc = themeDto.getDesc();
        this.price = themeDto.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
