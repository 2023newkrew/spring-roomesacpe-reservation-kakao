package roomescape.repository;

import roomescape.model.Theme;
import java.util.Optional;

public interface ThemeRepository {
    long save(Theme theme);
    Optional<Theme> findOneByName(String themeName);
    void deleteByName(String themeName);
}
