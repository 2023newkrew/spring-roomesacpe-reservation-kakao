package nextstep.dto.response;

import nextstep.Theme;

public class ThemeResponse {
    private Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    private ThemeResponse(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponse ofEntity(Theme theme) {
        return new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
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
