package reservation.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Theme {
    private Long id;
    private String name;
    private String desc;
    private Integer price;

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