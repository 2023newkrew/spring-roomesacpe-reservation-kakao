package roomescape.dto;

import roomescape.domain.Theme;

public class ThemeShowResponse {

    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public ThemeShowResponse(Long id, String name, String desc, Integer price) {
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

    public static ThemeShowResponse of(Theme theme) {
        return new ThemeShowResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }


}
