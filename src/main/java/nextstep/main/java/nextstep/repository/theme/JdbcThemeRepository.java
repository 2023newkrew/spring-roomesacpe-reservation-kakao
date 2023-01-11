package nextstep.main.java.nextstep.repository.theme;

import lombok.RequiredArgsConstructor;
import nextstep.main.java.nextstep.domain.theme.Theme;
import nextstep.main.java.nextstep.domain.theme.ThemeCreateRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcThemeRepository implements ThemeRepository{
    private static final String THEME_TABLE = "theme";
    private static final String PRIMARY_KEY = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(THEME_TABLE)
                .usingGeneratedKeyColumns(PRIMARY_KEY);
    }

    @Override
    public Long save(ThemeCreateRequestDto request) {
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(request)).longValue();
    }

    @Override
    public Optional<Theme> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Theme> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
