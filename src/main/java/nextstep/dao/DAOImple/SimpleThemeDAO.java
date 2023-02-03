package nextstep.dao.DAOImple;

import nextstep.dao.ThemeDAO;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleThemeDAO implements ThemeDAO {
    private static final RowMapper<Theme> THEME_ROW_MAPPER =
            (resultSet, rowNum) -> {
                return new Theme(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("desc"),
                        resultSet.getInt("price")
                );
            };

    @Override
    public Long insert(Theme theme) {
        try (Connection connection = getConnection()) {
            var ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();
            var rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Theme getById(Long id) {
        try (Connection connection = getConnection()) {
            var ps = connection.prepareStatement(SELECT_BY_ID_SQL);
            ps.setLong(1, id);
            var rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return THEME_ROW_MAPPER.mapRow(rs, 0);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Theme> getList() {
        try (Connection connection = getConnection()) {
            var ps = connection.prepareStatement(SELECT_SQL);
            var rs = ps.executeQuery();
            List<Theme> resultList = new ArrayList<>();

            while (rs.next()) {
                resultList.add(THEME_ROW_MAPPER.mapRow(rs, 0));
            }

            return resultList;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try (Connection connection = getConnection()) {
            var ps = connection.prepareStatement(DELETE_BY_ID_SQL);
            ps.setLong(1, id);
            var rs = ps.executeUpdate();
            return rs == 1;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
