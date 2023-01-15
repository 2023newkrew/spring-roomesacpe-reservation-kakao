package nextstep.domain.theme.repository;

import nextstep.domain.theme.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    Theme save(Theme theme);
    Optional<Theme> findByName(String name);
    List<Theme> findAll(int page, int offset);
    void deleteById(Long id);
    void update(Theme theme);

}
