package roomescape.theme.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.theme.domain.Theme;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ThemeRepositoryWeb extends AbstractThemeH2Repository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    ThemeRepositoryWeb(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Theme add(Theme theme) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(theme);
        theme.setId(insertActor.executeAndReturnKey(parameters).longValue());
        return theme;
    }

    @Override
    public List<Theme> get() {
        return jdbcTemplate.query(selectQuery, new ThemeMapper());
    }

    @Override
    public Theme get(Long id) {
        List<Theme> themes = jdbcTemplate.query(selectByIdQuery, new ThemeMapper(), id);
        if (themes.isEmpty()) {
            return null;
        }
        return themes.get(0);
    }

    @Override
    public Theme get(String name) {
        List<Theme> themes =  jdbcTemplate.query(selectByNameQuery, new ThemeMapper(), name);
        if (themes.isEmpty()) {
            return null;
        }
        return themes.get(0);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(deleteByIdQuery, id);
    }
}
