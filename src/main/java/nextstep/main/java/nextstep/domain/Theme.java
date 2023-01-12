package nextstep.main.java.nextstep.domain;

import java.util.Objects;

public class Theme {
    private Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public Theme(Long id, String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.id = id;
    }

    public Theme(String name, String desc, Integer price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(Long id, Theme theme) {
        this(id, theme.getName(), theme.getDesc(), theme.getPrice());
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
        return name.equals(theme.name) && desc.equals(theme.desc) && price.equals(theme.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, price);
    }
}
