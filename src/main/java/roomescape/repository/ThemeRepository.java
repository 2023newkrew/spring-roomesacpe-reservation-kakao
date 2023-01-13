package roomescape.repository;

import roomescape.domain.Theme;

public interface ThemeRepository {

    Long createTheme(Theme theme);
    Theme findThemeById(Long id);
    int updateTheme(Theme theme);
    int deleteTheme(Long id);


}
