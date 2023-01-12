package roomescape.repository;

import roomescape.model.Theme;
import java.util.Optional;

public interface ThemeRepository {
    long save(Theme theme);
    Optional<Theme> findOneById(Long themeId);
    Optional<Theme> findOneByName(String themeName);
    void delete(Long themeId);
    Boolean hasThemeWithName(String themeName);
}
