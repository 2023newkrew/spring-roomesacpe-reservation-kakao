package nextstep.theme.repository.jdbc;


import nextstep.theme.domain.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ThemeStatementCreator {

    private static final String INSERT_SQL = "INSERT INTO theme(name, desc, price) VALUES(?, ?, ?)";


    public PreparedStatement createInsert(
            Connection connection, Theme theme) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
        setTheme(ps, theme);

        return ps;
    }

    private void setTheme(PreparedStatement ps, Theme theme) throws SQLException {
        ps.setString(1, theme.getName());
        ps.setString(2, theme.getDesc());
        ps.setInt(3, theme.getPrice());
    }
}
