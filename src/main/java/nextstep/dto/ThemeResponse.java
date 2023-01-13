package nextstep.dto;

import nextstep.domain.Theme;

public class ThemeResponse {
    Long id;
    String name;
    String desc;
    Integer price;

    private ThemeResponse(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Long getId() {
        return id;
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

    public static ThemeResponse from(Theme theme) {
        return new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
