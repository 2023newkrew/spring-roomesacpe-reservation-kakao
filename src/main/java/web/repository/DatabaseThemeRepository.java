package web.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import web.entity.Theme;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class DatabaseThemeRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseThemeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long createTheme(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, String.valueOf(theme.getName()));
                ps.setString(2, String.valueOf(theme.getDesc()));
                ps.setInt(3, theme.getPrice());
                return ps;
            }, keyHolder);
            return keyHolder.getKey().longValue();
        } catch (Exception E) {
            return null;
        }
    }


    public long deleteTheme(long themeId) {
        String sql = "DELETE FROM theme WHERE ID = ?";
        return jdbcTemplate.update(sql, themeId);
    }

    @Override
    public Optional<List<Theme>> getThemes() {
        String sql = "SELECT * FROM theme;";
        try {
            List<Theme> themes = jdbcTemplate.query(sql,
                    (resultSet, rowNum) -> Theme.of(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("desc"),
                            resultSet.getInt("price")));
            return Optional.ofNullable(themes);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
