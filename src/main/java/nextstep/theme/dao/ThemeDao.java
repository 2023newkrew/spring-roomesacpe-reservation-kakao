package nextstep.theme.dao;

import nextstep.theme.entity.Theme;

import java.util.Optional;

public interface ThemeDao {
    Optional<Theme> findById(Long id);

    int insert(Theme theme);
}
