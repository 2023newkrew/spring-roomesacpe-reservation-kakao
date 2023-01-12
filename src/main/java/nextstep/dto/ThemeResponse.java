package nextstep.dto;

import nextstep.model.Theme;

public class ThemeResponse {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public ThemeResponse(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public ThemeResponse(Theme theme) {
        this(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
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
