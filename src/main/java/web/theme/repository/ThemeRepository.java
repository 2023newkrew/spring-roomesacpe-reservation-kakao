package web.theme.repository;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import web.entity.Theme;
import web.theme.exception.ErrorCode;
import web.theme.exception.ThemeException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long save(Theme theme) {
        if (isDuplicateTheme(theme)) {
            throw new ThemeException(ErrorCode.THEME_DUPLICATE);
        }
        String sql = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setLong(3, theme.getPrice());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private boolean isDuplicateTheme(Theme theme) {
        String sql = "SELECT * FROM THEME WHERE NAME = ?";
        Theme findTheme;
        try {
            findTheme = jdbcTemplate.queryForObject(sql, themeRowMapper(), theme.getName());
        } catch (DataAccessException e) {
            return false;
        }
        return findTheme != null;
    }

    public Optional<Theme> findById(long themeId) {
        String sql = "SELECT * FROM THEME WHERE ID = ?";
        Theme findTheme;
        try {
            findTheme = jdbcTemplate.queryForObject(sql, themeRowMapper(), themeId);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(findTheme);
    }

    public Long delete(long themeId) {
        return null;
    }

    public void clearAll() {
        String sql = "DELETE FROM THEME";
        jdbcTemplate.update(sql);
    }

    private RowMapper<Theme> themeRowMapper() {
        return (rs, rowNum) -> {
            Theme theme = Theme.of(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price")
            );
            return theme;
        };
    }
}
