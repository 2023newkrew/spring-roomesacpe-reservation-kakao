package nextstep.model;

import java.util.Objects;

public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(Long id, String name, String desc, Integer price) {
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

    @Override
    public String toString() {
        return "Theme{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }
}
