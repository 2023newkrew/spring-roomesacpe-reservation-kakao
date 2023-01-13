package roomescape.theme.dto;

import roomescape.entity.Theme;

public class ThemeResponseDto {
    private Long themeId;
    private String name;
    private String desc;
    private Integer price;

    public ThemeResponseDto(Long themeId, String name, String desc, Integer price) {
        this.themeId = themeId;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponseDto of(Theme theme) {
        return new ThemeResponseDto(theme.getThemeId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
