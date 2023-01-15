package roomescape.theme.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.theme.domain.Theme;

@Repository
public class ThemeJdbcTemplateRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ThemeJdbcTemplateRepository(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Theme> findById(final Long id) {
        final String selectSql = "SELECT * FROM theme WHERE id = (?) LIMIT 1 ";

        final List<Theme> themes = jdbcTemplate.query(selectSql, (rs, rowNum) -> Theme.from(rs),
                id);

        if (themes.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(themes.get(0));
    }

    @Override
    public Optional<Theme> findByName(final String themeName) {

        final String selectSql = "SELECT * FROM theme WHERE name = (?) LIMIT 1 ";

        final List<Theme> themes = jdbcTemplate.query(selectSql, (rs, rowNum) -> Theme.from(rs),
                themeName);

        if (themes.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(themes.get(0));
    }

    @Override
    public Theme save(final Theme theme) {
        Long themeId = this.insertActor.executeAndReturnKey(theme.buildParams()).longValue();
        theme.setId(themeId);
        return theme;
    }

    @Override
    public boolean deleteById(final Long id) {
        return false;
    }
}
