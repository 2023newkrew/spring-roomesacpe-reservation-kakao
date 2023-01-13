package roomescape.controller.dto;

import roomescape.domain.Theme;

import java.util.List;
import java.util.stream.Collectors;

public class ThemeResponse {
    private final Long id;
    private final String name;
    private final String desc;
    private final int price;

    public ThemeResponse(Long id, String name, String desc, int price) {
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

    public int getPrice() {
        return price;
    }

    public static ThemeResponse of(Theme theme) {
        return new ThemeResponse(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }

    public static List<ThemeResponse> toList(List<Theme> themes) {
        return themes.stream()
                .map(ThemeResponse::of)
                .collect(Collectors.toList());
    }
}
