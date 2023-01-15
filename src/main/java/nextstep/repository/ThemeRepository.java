package nextstep.repository;

import nextstep.domain.Theme;

import java.util.List;

public interface ThemeRepository {

    void add(Theme theme);
    Theme findById(Long id);
    List<Theme> findAll();
    void update(Theme theme);
    void deleteById(Long id);
}
