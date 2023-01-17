package roomescape.repository.Theme;

import roomescape.domain.Theme;

import java.util.Optional;

public interface ThemeRepository {
    Long createTheme(Theme theme);
    Optional<Theme> findById(long themeId);
    Long findIdByDateAndTime(Theme theme);
    Integer deleteTheme(long deleteId);
}
