package nextstep.domain.theme.repository;

import nextstep.domain.theme.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static nextstep.domain.QuerySetting.Theme.*;

@Repository
public class JdbcTemplateThemeRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(PK_NAME);
    }

    @Override
    public Theme save(Theme theme) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(theme);
        long themeId = jdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return new Theme(themeId, theme);
    }

    @Override
    public Optional<Theme> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Theme> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
