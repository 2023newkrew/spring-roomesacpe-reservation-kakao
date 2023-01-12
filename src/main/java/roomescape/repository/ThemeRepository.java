package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class ThemeRepository {

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

    public ThemeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
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

    public Theme findThemeById(Long id) {
        String sql = "select * from theme where id = ?";
        return jdbcTemplate.query(sql, actorRowMapper, id).stream()
                .findFirst()
                .orElse(null);
    }

    public Theme findThemeByName(String name) {
        String sql = "select * from theme where name = ?";
        return jdbcTemplate.query(sql, actorRowMapper, name).stream()
                .findFirst()
                .orElse(null);
    }

    public int updateTheme(Theme theme) {
        String sql = "update theme set name = ?, desc = ?, price = ? where id = ?";
        return jdbcTemplate.update(sql, theme.getName(), theme.getDesc(), theme.getPrice(), theme.getId());
    }

    public int deleteTheme(Long id) {
        String sql = "delete from theme where id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
