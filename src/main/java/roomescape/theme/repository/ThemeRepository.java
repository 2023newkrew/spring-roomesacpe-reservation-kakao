package roomescape.theme.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.theme.domain.Theme;

import java.sql.PreparedStatement;

@Repository
public class ThemeRepository {

    private static final int INDEX_NAME = 1;
    private static final int INDEX_DESC = 2;
    private static final int INDEX_THEME_NAME = 3;
    final JdbcTemplate jdbcTemplate;
    private final RowMapper<Theme> themeRowMapper =
            (resultSet, rowNum) -> ThemeMapper.mapToTheme(resultSet);

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Theme theme) {
        String sql = "INSERT INTO THEME (name, desc, price) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(INDEX_NAME, theme.getName());
            ps.setString(INDEX_DESC, theme.getDesc());
            ps.setInt(INDEX_THEME_NAME, theme.getPrice());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Theme findById(String themeId) {
        String sql = "SELECT * FROM THEME WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, themeRowMapper, Long.parseLong(themeId));
    }

    public void deleteById(String themeId) {
        String sql = "DELETE FROM THEME WHERE id = ?";
        jdbcTemplate.update(sql, themeId);
    }
}
