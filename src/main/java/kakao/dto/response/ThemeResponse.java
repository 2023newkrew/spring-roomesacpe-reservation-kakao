package kakao.dto.response;

import kakao.domain.Theme;

public class ThemeResponse {

    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
    }
}
