package reservation.respository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import reservation.domain.Theme;

import java.sql.PreparedStatement;
import java.util.Objects;

@Repository
public class ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createTheme(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Theme getTheme(Long themeId) {
        String sql = "SELECT id, name, desc, price FROM theme WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Theme(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price")
            ), themeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deleteTheme(Long themeId) {
        String sql = "DELETE FROM theme WHERE id = ?";
        return jdbcTemplate.update(sql, themeId);
    }

    public boolean existTheme(Theme theme) {
        String sql = "SELECT EXISTS(SELECT 1 FROM theme WHERE name = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, theme.getName()));
    }
}
