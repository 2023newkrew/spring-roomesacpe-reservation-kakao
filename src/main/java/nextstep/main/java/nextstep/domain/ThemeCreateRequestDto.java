package nextstep.main.java.nextstep.domain;

import java.util.Objects;

public class ThemeCreateRequestDto {
    private final String name;
    private final String desc;
    private final int price;

    public ThemeCreateRequestDto(String name, String desc, int price) {
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

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemeCreateRequestDto that = (ThemeCreateRequestDto) o;
        return price == that.price && name.equals(that.name) && desc.equals(that.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc, price);
    }
}
