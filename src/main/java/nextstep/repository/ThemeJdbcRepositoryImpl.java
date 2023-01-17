package nextstep.repository;

import static nextstep.repository.ThemeJdbcSql.FIND_BY_ID_STATEMENT;
import static nextstep.repository.ThemeJdbcSql.INSERT_INTO_STATEMENT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import nextstep.console.utils.ConnectionHandler;
import nextstep.entity.Theme;

public class ThemeJdbcRepositoryImpl implements ThemeRepository {

    private final ConnectionHandler connectionHandler;

    public ThemeJdbcRepositoryImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Theme save(Theme theme) {
        PreparedStatement ps;
        ResultSet rs;
        Theme entity = null;
        try {
            ps = connectionHandler.createPreparedStatement(INSERT_INTO_STATEMENT,
                    new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong("id");
                entity = Theme.createTheme(Theme
                                .builder()
                                .name(rs.getString("name"))
                                .price(rs.getInt("price"))
                                .description(rs.getString("desc"))
                                .build(),
                        id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releaseResultSet(rs);
        releasePreparedStatement(ps);
        return entity;

    }

    @Override
    public Optional<Theme> findById(Long id) {
        Optional<Theme> theme = Optional.empty();
        PreparedStatement ps;
        try {
            ps = connectionHandler.createPreparedStatement(FIND_BY_ID_STATEMENT, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                theme = Optional.of(
                        makeTheme(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releasePreparedStatement(ps);
        return theme;
    }

    @Override
    public int update(Theme theme) {
        PreparedStatement ps;
        int row = 0;
        try {
            ps = connectionHandler.createPreparedStatement(ThemeJdbcSql.UPDATE_STATEMENT, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setInt(3, theme.getPrice());
            ps.setLong(4, theme.getId());
            row = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releasePreparedStatement(ps);
        return row;
    }

    @Override
    public int deleteById(Long id) {
        PreparedStatement ps;
        int row;
        try {
            ps = connectionHandler.createPreparedStatement(ThemeJdbcSql.DELETE_BY_ID_STATEMENT, new String[]{"id"});
            ps.setLong(1, id);
            row = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releasePreparedStatement(ps);
        return row;
    }

    private static void releaseResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void releasePreparedStatement(PreparedStatement ps) {
        try {
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Theme makeTheme(ResultSet resultSet) throws SQLException {
        return Theme.createTheme(Theme.builder()
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("desc"))
                        .price(resultSet.getInt("price"))
                        .build(),
                resultSet.getLong("id"));
    }
}
