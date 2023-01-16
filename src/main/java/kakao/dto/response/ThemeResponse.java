package kakao.dto.response;

import kakao.domain.Theme;

public class ThemeResponse {

    public final Long id;
    public final String name;
    public final String desc;
    public final Integer price;

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
    }
}
