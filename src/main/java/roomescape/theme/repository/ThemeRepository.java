package roomescape.theme.repository;

import java.util.List;
import java.util.Optional;
import roomescape.theme.domain.Theme;

public interface ThemeRepository {

    Optional<Theme> findById(Long id);

    Optional<Theme> findByName(String themeName);

    Theme save(Theme theme);

    boolean deleteById(Long id);

    List<Theme> findAll();
}
