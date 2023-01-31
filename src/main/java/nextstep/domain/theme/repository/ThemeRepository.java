package nextstep.domain.theme.repository;

import nextstep.domain.theme.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Optional<Theme> findByName(String name);

    Long save(Theme theme);

    Optional<Theme> findById(Long id);

    List<Theme> findAll();

    void update(Theme theme);

    void delete(Long id);

    void clear();

}
