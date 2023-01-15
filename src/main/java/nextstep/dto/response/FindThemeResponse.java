package nextstep.dto.response;

import nextstep.domain.theme.Theme;

public class FindThemeResponse {

    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public static FindThemeResponse from(Theme theme) {
        return new FindThemeResponse(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }

    private FindThemeResponse(Long id, String name, String desc, Integer price) {
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
}
