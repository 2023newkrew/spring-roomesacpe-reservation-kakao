package nextstep.dao;

import nextstep.entity.Theme;

public interface ThemeDao {
    Theme findById(Long id);

    Long insert(Theme theme);

}
