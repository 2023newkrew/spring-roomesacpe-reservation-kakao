package web.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.entity.Theme;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeDaoImpl implements ThemeDao {
    private final JdbcTemplate jdbcTemplate;

    public ThemeDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Long deleteTheme(long themeId) {
        String sql = "DELETE FROM theme WHERE ID = ?";
        return (long) jdbcTemplate.update(sql, themeId);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Optional<Theme> findThemeById(long themeId) {
        String sql = "SELECT * FROM theme WHERE ID = ?;";
        Theme theme;
        try {
            theme = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> Theme.of(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price")), themeId);
            return Optional.ofNullable(theme);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
