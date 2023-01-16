package nextstep.theme.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nextstep.theme.entity.Theme;

@ToString
@Getter
@EqualsAndHashCode
public class ThemeDetail {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public ThemeDetail(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
    }

    public ThemeDetail(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
