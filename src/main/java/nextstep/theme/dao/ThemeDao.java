package nextstep.theme.dao;

import nextstep.theme.entity.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeDao {
    Optional<Theme> findById(Long id);
    Optional<Theme> findByName(String name);

    List<Theme> findAll();
    int insert(Theme theme);
    int update(Theme theme);
    int deleteById(Long id);
}
