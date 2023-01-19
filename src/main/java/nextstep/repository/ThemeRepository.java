package nextstep.repository;

import nextstep.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    void save(Theme theme);
    Optional<Theme> findById(Long id);
    List<Theme> findAll();
    void update(Long id, Theme theme);
    void deleteById(Long id);
}
