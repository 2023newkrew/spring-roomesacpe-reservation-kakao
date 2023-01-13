package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;
import roomescape.dto.ThemeUpdateRequest;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class ThemeAppRepository implements ThemeRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;
    private final RowMapper<Theme> actorRowMapper = (rs, rowNum) -> {
        Theme theme = new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
        );
        return theme;
    };

    public ThemeAppRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    public Long createTheme(Theme theme) {
        Map themeInfo = Map.of(
                "name", theme.getName(),
                "desc", theme.getDesc(),
                "price", theme.getPrice());
        return insertActor.executeAndReturnKey(themeInfo).longValue();
    }

    public Optional<Theme> findThemeById(Long id) {
        String sql = "SELECT * FROM theme WHERE id = ?;";
        return jdbcTemplate.query(sql, actorRowMapper, id).stream().findFirst();
    }

    public int updateTheme(ThemeUpdateRequest themeUpdateRequest, Long id) {
        String sql = "UPDATE theme SET name = ?, desc = ?, price = ? WHERE id = ?;";
        return jdbcTemplate.update(sql, themeUpdateRequest.getName(), themeUpdateRequest.getDesc(), themeUpdateRequest.getPrice(), id);
    }

    public int deleteTheme(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?;";
        return jdbcTemplate.update(sql, id);
    }

}
