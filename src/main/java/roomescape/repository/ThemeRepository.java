package roomescape.repository;

import roomescape.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    List<Theme> getAllThemes();

    Optional<Theme> getTheme(Long id);

    Long insertTheme(Theme theme);

    void changeTheme(Long id, String name, String desc, int price);

    void deleteTheme(Long id);
}
