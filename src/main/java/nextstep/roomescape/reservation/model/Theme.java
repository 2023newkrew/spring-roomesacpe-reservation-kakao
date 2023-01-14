package nextstep.roomescape.reservation.model;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return Objects.equals(name, theme.name) && Objects.equals(desc, theme.desc) && Objects.equals(price, theme.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc, price);
    }
}
