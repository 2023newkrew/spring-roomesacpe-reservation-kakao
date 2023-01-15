package nextstep.theme.dao;

import nextstep.theme.entity.Theme;

import java.sql.SQLException;

public interface ThemeDao {
    Theme findById(Long id);

    int insert(Theme theme);
}
