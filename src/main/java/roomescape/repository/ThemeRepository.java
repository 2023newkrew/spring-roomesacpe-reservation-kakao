package roomescape.repository;

import roomescape.model.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Long save(Theme theme);

    Optional<Theme> find(Long id);

    List<Theme> findAll();

    Boolean delete(Long id);

    Boolean existsByName(String name);
}
