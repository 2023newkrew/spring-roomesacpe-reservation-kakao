package nextstep.web.repository;

import nextstep.domain.Theme;

import java.util.List;

public interface ThemeRepository {

    Long save(Theme theme);

    List<Theme> findAll();

    void deleteById(Long id);
}
