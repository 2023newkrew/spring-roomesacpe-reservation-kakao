package nextstep.repository;

import static nextstep.repository.ThemeJdbcSql.FIND_BY_ID;
import static nextstep.repository.ThemeJdbcSql.INSERT_INTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import nextstep.dto.ConnectionHandler;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;

public class ThemeJdbcRepositoryImpl implements ThemeRepository {

    private final ConnectionHandler connectionHandler;

    public ThemeJdbcRepositoryImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public Long save(ThemeCreateDto themeCreateDto){
        PreparedStatement ps;
        ResultSet rs;
        Long id = null;
        try {
            ps = connectionHandler.createPreparedStatement(INSERT_INTO,
                    new String[]{"id"});
            ps.setString(1, themeCreateDto.getName());
            ps.setString(2, themeCreateDto.getDescription());
            ps.setInt(3, themeCreateDto.getPrice());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getLong("id");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        releaseResultSet(rs);
        releasePreparedStatement(ps);
        return id;

    }

    @Override
    public Optional<Theme> findById(Long id) {
        Optional<Theme> theme = Optional.empty();
        PreparedStatement ps;
        try {
            ps = connectionHandler.createPreparedStatement(FIND_BY_ID, new String[]{"id"});
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
    public int update(ThemeEditDto themeEditDto) {
        PreparedStatement ps;
        int row = 0;
        try {
            ps = connectionHandler.createPreparedStatement(ThemeJdbcSql.UPDATE, new String[]{"id"});
            ps.setLong(1, themeEditDto.getId());
            ps.setString(2, themeEditDto.getName());
            ps.setString(3, themeEditDto.getDescription());
            ps.setInt(4, themeEditDto.getPrice());
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
        try{
            ps = connectionHandler.createPreparedStatement(ThemeJdbcSql.DELETE_BY_ID, new String[]{"id"});
            ps.setLong(1, id);
            row = ps.executeUpdate();
        }catch (SQLException e){
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
        return new Theme(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price"));
    }
}
