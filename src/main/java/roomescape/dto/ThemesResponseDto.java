package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import roomescape.model.Theme;

import java.util.List;

public class ThemesResponseDto {
    @JsonValue
    private final List<Theme> themes;

    public ThemesResponseDto(List<Theme> themes) {
        this.themes = themes;
    }

    public List<Theme> getThemes() {
        return themes;
    }
}
