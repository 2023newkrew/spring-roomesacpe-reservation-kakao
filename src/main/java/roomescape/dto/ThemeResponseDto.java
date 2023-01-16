package roomescape.dto;

import roomescape.model.Theme;

import java.util.Objects;

public class ThemeResponseDto {
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;

    public ThemeResponseDto(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.desc = theme.getDesc();
        this.price = theme.getPrice();
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ThemeResponseDto) || obj == null) return false;
        ThemeResponseDto cp = (ThemeResponseDto) obj;
        return id.equals(cp.id) && name.equals(cp.name) && desc.equals(cp.desc) && price.equals(cp.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, price);
    }
}
