package nextstep.dao;

import nextstep.entity.Theme;

import java.sql.SQLException;

public interface ThemeDao {
    Theme findById(Long id) throws SQLException;

    Long insert(Theme theme);

}
