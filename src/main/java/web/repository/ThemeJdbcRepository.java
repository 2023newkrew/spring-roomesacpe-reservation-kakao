package web.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import web.domain.Theme;
import web.dto.response.ThemeIdDto;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findAllThemeByName(Theme theme) {
        String selectSql = "SELECT id FROM theme WHERE name = (?) LIMIT 1 ";

        return jdbcTemplate.query(selectSql, ((rs, rowNum) ->
                rs.getLong("id")), String.valueOf(theme.getName()));

    }

    public ThemeIdDto createTheme(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(theme.getName()));
            ps.setString(2, String.valueOf(theme.getDesc()));
            ps.setInt(3, theme.getPrice());
            return ps;
        }, keyHolder);

        return new ThemeIdDto((Long) keyHolder.getKey());
    }

}
