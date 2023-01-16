package roomescape.dto;

import java.util.List;

public class ThemesResponseDto {
    private final List<ThemeResponseDto> themes;

    public ThemesResponseDto(List<ThemeResponseDto> themes) {
        this.themes = themes;
    }

    public List<ThemeResponseDto> getThemes() {
        return themes;
    }
}
