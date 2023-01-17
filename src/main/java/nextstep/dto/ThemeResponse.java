package nextstep.dto;

import nextstep.domain.Theme;

import java.util.Objects;

public class ThemeResponse {
    Long id;
    String name;
    String desc;
    Integer price;

    public ThemeResponse(Long id, String name, String desc, Integer price) {
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
        ThemeResponse that = (ThemeResponse) o;
        return id.equals(that.id) && name.equals(that.name) && desc.equals(that.desc) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, price);
    }

    public static ThemeResponse from(Theme theme) {
        return new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
