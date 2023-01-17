package nextstep.dto;

import java.util.List;

public class ThemesResponse {
    private List<ThemeResponse> themes;

    public ThemesResponse(List<ThemeResponse> themes) {
        this.themes = themes;
    }

    public List<ThemeResponse> getThemes() {
        return themes;
    }
}
