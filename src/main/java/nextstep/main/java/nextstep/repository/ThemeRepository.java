package nextstep.main.java.nextstep.repository;

import nextstep.main.java.nextstep.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Theme save(Theme theme);

    Optional<Theme> findById(Long id);

    List<Theme> findAll();

    int update(Theme theme);

    void deleteById(Long id);
}
