package nextstep.domain.dto;

import nextstep.domain.theme.Theme;

public class ThemeResponse {
    private Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
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
}
