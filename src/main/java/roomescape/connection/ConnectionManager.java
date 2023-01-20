package roomescape.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class ConnectionManager {

    private final DataSource dataSource;

    public ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) {
        Connection con = null;
        try {
            con = DataSourceUtils.getConnection(dataSource);
            PreparedStatement ps = psc.createPreparedStatement(con);
            ResultSet resultSet = ps.executeQuery();
            return new RowMapperResultSetExtractor<>(rowMapper).extractData(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> resultSetExtractor) {
        Connection con = null;
        try {
            con = DataSourceUtils.getConnection(dataSource);
            PreparedStatement ps = psc.createPreparedStatement(con);
            ResultSet resultSet = ps.executeQuery();
            return resultSetExtractor.extractData(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    public void update(PreparedStatementCreator psc) {
        Connection con = null;
        try {
            con = DataSourceUtils.getConnection(dataSource);
            PreparedStatement ps = psc.createPreparedStatement(con);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    public <T> T updateAndGetKey(PreparedStatementCreator psc, String key, Class<T> tClass) {
        Connection con = null;
        try {
            con = DataSourceUtils.getConnection(dataSource);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }
}
