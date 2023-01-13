package nextstep.repository;

import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcThemeRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("THEME")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(Theme theme) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(theme);

        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public Optional<Theme> findThemeById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Theme> getAllThemes() {
        return null;
    }

    @Override
    public boolean deleteThemeById(Long id) {
        return false;
    }
}
