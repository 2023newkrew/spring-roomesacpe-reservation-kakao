package roomescape.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import roomescape.exception.BadRequestException;

public class ConnectionDAOManager {

    private final String url;
    private final String user;
    private final String password;

    public ConnectionDAOManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection openConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new BadRequestException();
            }
        }
    }

    public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) {
        Connection con = openConnection();
        try {
            PreparedStatement ps = psc.createPreparedStatement(con);
            ResultSet resultSet = ps.executeQuery();
            return new RowMapperResultSetExtractor<>(rowMapper).extractData(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }

    public void update(PreparedStatementCreator psc) {
        Connection con = openConnection();
        try {
            PreparedStatement ps = psc.createPreparedStatement(con);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }

    public <T> List<T> updateAndGetKey(PreparedStatementCreator psc, String key, Class<T> tClass) {
        Connection con = openConnection();
        try {
            PreparedStatement ps = psc.createPreparedStatement(con);
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            RowMapper<T> longRowMapper = (rs, rowNum) -> rs.getObject(key, tClass);
            return new RowMapperResultSetExtractor<>(longRowMapper).extractData(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }
}
