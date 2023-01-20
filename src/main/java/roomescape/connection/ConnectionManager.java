package roomescape.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

public class ConnectionManager {

    private final ConnectionPool connectionPool;

    public ConnectionManager(ConnectionSetting connectionSetting, PoolSetting connectionPoolSetting) {
        try {
            connectionPool = new ConnectionPool(connectionSetting, connectionPoolSetting);
        } catch (Exception e) {
            throw new RuntimeException("커넥션을 생성할 수 없습니다");
        }
    }

    public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            PreparedStatement ps = psc.createPreparedStatement(con);
            ResultSet resultSet = ps.executeQuery();
            return new RowMapperResultSetExtractor<>(rowMapper).extractData(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> resultSetExtractor) {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            PreparedStatement ps = psc.createPreparedStatement(con);
            ResultSet resultSet = ps.executeQuery();
            return resultSetExtractor.extractData(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public void update(PreparedStatementCreator psc) {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            PreparedStatement ps = psc.createPreparedStatement(con);
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public <T> T updateAndGetKey(PreparedStatementCreator psc, String key, Class<T> tClass) {
        Connection con = null;
        try {
            con = connectionPool.getConnection();
            PreparedStatement ps = psc.createPreparedStatement(con);
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            ResultSetExtractor<T> extractor = rs -> {
                if (!rs.next()) {
                    return null;
                }
                return rs.getObject(key, tClass);
            };
            return extractor.extractData(resultSet);
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public void close() {
        connectionPool.close();
    }
}
