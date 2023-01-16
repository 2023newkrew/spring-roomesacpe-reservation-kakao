package nextstep.theme.repository.jdbc;


import nextstep.theme.domain.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ThemeStatementCreator {

    private static final String INSERT_SQL = "INSERT INTO theme(name, desc, price) VALUES(?, ?, ?)";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM theme WHERE id = ?";

    private static final String SELECT_ALL_SQL = "SELECT * FROM theme";

    private static final String DELETE_BY_ID_SQL = "DELETE FROM theme WHERE id = ?";


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

    public PreparedStatement createSelectById(Connection connection, Long id) throws SQLException {
        return createById(connection, SELECT_BY_ID_SQL, id);
    }

    private PreparedStatement createById(
            Connection connection, String sql, Long id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }


    public PreparedStatement createSelectAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SELECT_ALL_SQL);
    }

    public PreparedStatement createDeleteById(Connection connection, Long id) throws SQLException {
        return createById(connection, DELETE_BY_ID_SQL, id);
    }
}
