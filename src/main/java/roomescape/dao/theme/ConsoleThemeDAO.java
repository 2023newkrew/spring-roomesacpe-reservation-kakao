package roomescape.dao.theme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ExistThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.RemoveThemePreparedStatementCreator;
import roomescape.dto.Theme;
import roomescape.exception.BadRequestException;

public class ConsoleThemeDAO extends ThemeDAO {

    private final String url;
    private final String user;
    private final String password;

    public ConsoleThemeDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection openConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validateResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new SQLException();
        }
    }

    private PreparedStatement createAddThemePreparedStatement(
            Connection con, Theme theme) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new InsertThemePreparedStatementCreator(theme);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createListThemePreparedStatement(
            Connection con) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new ListThemePreparedStatementCreator();
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createDeleteThemePreparedStatement(
            Connection con, Long id) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new RemoveThemePreparedStatementCreator(id);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private PreparedStatement createExistThemePreparedStatement(
            Connection con, Theme theme) throws SQLException {
        PreparedStatementCreator preparedStatementCreator =
                new ExistThemePreparedStatementCreator(theme);
        return preparedStatementCreator.createPreparedStatement(con);
    }

    private Long getGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet resultSet = ps.getGeneratedKeys();
        validateResultSet(resultSet);
        return resultSet.getLong(1);
    }

    private List<Theme> parseListResultSet(ResultSet resultSet) throws SQLException {
        List<Theme> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(getRowMapper().mapRow(resultSet, 1));
        }
        return result;
    }

    private boolean parseExistResultSet(ResultSet resultSet) throws SQLException {
        validateResultSet(resultSet);
        return resultSet.getBoolean("result");
    }

    private Long executeAddConnection(Connection con, Theme theme) {
        try {
            PreparedStatement ps = createAddThemePreparedStatement(con, theme);
            ps.executeUpdate();
            return getGeneratedKey(ps);
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private List<Theme> executeListConnection(Connection con) {
        try {
            PreparedStatement ps = createListThemePreparedStatement(con);
            ResultSet resultSet = ps.executeQuery();
            return parseListResultSet(resultSet);
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private void executeDeleteConnection(Connection con, Long id) {
        try {
            PreparedStatement ps = createDeleteThemePreparedStatement(con, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private boolean executeExistConnection(Connection con, Theme theme) {
        try {
            PreparedStatement ps = createExistThemePreparedStatement(con, theme);
            ResultSet resultSet = ps.executeQuery();
            return parseExistResultSet(resultSet);
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    @Override
    public boolean exist(Theme theme) {
        Connection con = openConnection();
        boolean result = executeExistConnection(con, theme);
        closeConnection(con);
        return result;
    }

    @Override
    public Long create(Theme theme) {
        validate(theme);
        Connection con = openConnection();
        Long id = executeAddConnection(con, theme);
        closeConnection(con);
        return id;
    }

    @Override
    public List<Theme> list() {
        Connection con = openConnection();
        List<Theme> result = executeListConnection(con);
        closeConnection(con);
        return result;
    }

    @Override
    public void remove(Long id) {
        Connection con = openConnection();
        executeDeleteConnection(con, id);
        closeConnection(con);
    }
}
