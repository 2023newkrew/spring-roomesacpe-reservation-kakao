package nextstep.theme.dao;

import nextstep.theme.entity.Theme;

import java.sql.SQLException;

public interface ThemeDao {
    Theme findById(Long id) throws SQLException;

    int insert(Theme theme) throws SQLException;

}
