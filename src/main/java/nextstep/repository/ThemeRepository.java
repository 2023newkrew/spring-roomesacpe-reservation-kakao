package nextstep.repository;

import java.util.List;
import java.util.Optional;
import nextstep.model.Theme;

public interface ThemeRepository {
    Theme save(Theme theme);

    List<Theme> findAll();

    void deleteById(Long id);

    void deleteAll();

    Optional<Theme> findById(Long themeId);
}
