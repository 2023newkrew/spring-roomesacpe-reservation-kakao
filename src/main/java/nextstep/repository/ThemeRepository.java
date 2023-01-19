package nextstep.repository;

import nextstep.domain.Theme;

import java.util.List;

public interface ThemeRepository {

    void save(Theme theme);
    Theme findById(Long id);
    List<Theme> findAll();
    void update(Long id, Theme theme);
    void deleteById(Long id);
}
