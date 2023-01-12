package domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Theme {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(String name, String desc, Integer price) {
        this.id = null;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
