package roomescape.repository.Theme;

import roomescape.domain.Theme;

import java.util.Optional;

public interface ThemeRepository {
    Long createTheme(Theme theme);
    Optional<Theme> findThemeById(long themeId);
    Long findCountByNameAndPrice(Theme theme);
    Integer deleteTheme(long deleteId);
    Boolean isThemeExists(long themeId);

}
