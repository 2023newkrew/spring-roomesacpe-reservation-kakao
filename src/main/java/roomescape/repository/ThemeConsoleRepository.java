package roomescape.repository;

import roomescape.domain.Theme;

public class ThemeConsoleRepository implements ThemeRepository {
    @Override
    public Long createTheme(Theme theme) {
        return null;
    }

    @Override
    public Theme findThemeById(Long id) {
        return null;
    }

    @Override
    public Theme findThemeByName(String name) {
        return null;
    }

    @Override
    public int updateTheme(Theme theme) {
        return 0;
    }

    @Override
    public int deleteTheme(Long id) {
        return 0;
    }
}
