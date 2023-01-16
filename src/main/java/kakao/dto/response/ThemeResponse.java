package kakao.dto.response;

import domain.Theme;

public class ThemeResponse {
    public long id;
    public String name;
    public String desc;
    public int price;

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
    }
}
