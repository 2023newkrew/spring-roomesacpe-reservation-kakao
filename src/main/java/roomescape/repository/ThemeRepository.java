package roomescape.repository;

import roomescape.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    List<Theme> findAllThemes();

    Optional<Theme> findThemeById(Long id);

    Optional<Theme> findThemeByName(String name);

    Long insertTheme(Theme theme);

    void changeTheme(Long id, String name, String desc, int price);

    void deleteTheme(Long id);
}
