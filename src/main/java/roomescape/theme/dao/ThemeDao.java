package roomescape.theme.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.theme.domain.Theme;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ThemeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;


    public ThemeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }
    public Theme addTheme(Theme theme) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(theme);
        theme.setId(insertActor.executeAndReturnKey(parameters).longValue());
        return theme;
    }

    public List<Theme> findAllTheme() {
        String sql = "SELECT * FROM THEME";
        return jdbcTemplate.query(sql, new ThemeMapper());
    }
}
