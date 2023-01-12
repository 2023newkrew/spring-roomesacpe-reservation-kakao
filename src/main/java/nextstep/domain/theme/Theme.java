package nextstep.domain.theme;

import java.util.Objects;

public class Theme {
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
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

    @Override
    public boolean equals(Object obj) {
        Theme theme = (Theme) obj;
        return this.name.equals(theme.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
