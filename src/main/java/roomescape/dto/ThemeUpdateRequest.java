package roomescape.dto;

import roomescape.domain.Theme;

public class ThemeUpdateRequest {
    private String name;
    private String desc;
    private Integer price;

    public ThemeUpdateRequest(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
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

    public void fill(Theme theme) {
        if (name == null) {
            name = theme.getName();
        }
        if (desc == null) {
            desc = theme.getDesc();
        }
        if (price == null) {
            price = theme.getPrice();
        }
    }

}
