package nextstep.step3.dao;

import nextstep.step3.entity.Theme;

import java.sql.SQLException;

public interface ThemeDao {
    Theme findById(Long id) throws SQLException;

    int insert(Theme theme) throws SQLException;

}
