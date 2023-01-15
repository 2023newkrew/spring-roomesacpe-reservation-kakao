package nextstep.step3.theme.dao;

import nextstep.step3.theme.entity.Theme;

import java.sql.SQLException;

public interface ThemeDao {
    Theme findById(Long id) throws SQLException;

    int insert(Theme theme) throws SQLException;

}
