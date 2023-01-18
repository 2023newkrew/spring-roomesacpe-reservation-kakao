package nextstep.dto.web.request;

import nextstep.domain.Theme;

public class CreateThemeRequest {
    private final String name;
    private final String desc;
    private final Integer price;

    private CreateThemeRequest(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static CreateThemeRequest of(String name, String desc, Integer price) {
        return new CreateThemeRequest(name, desc, price);
    }

    public Theme toEntity() {
        return Theme.of(null, name, desc, price);
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
