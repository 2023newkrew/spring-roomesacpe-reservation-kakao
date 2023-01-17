package nextstep.roomescape.theme.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
public class Theme {

    @Nullable
    private final Long id;
    private final String name;
    private final String desc;
    private final Integer price;


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
