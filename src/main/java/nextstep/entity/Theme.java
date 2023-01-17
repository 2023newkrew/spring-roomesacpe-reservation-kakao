package nextstep.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Theme {

    private Long id;

    private final String name;

    private final String description;

    private final Integer price;

    @Builder
    public Theme(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public static Theme createTheme(Theme other, Long id) {
        Theme theme = Theme.builder()
                .description(other.description)
                .name(other.getName())
                .price(other.price).build();
        theme.id = id;
        return theme;
    }


}
