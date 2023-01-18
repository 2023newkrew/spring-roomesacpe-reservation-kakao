package nextstep.repository;

import static nextstep.repository.ThemeJdbcSql.DELETE_BY_ID_STATEMENT;
import static nextstep.repository.ThemeJdbcSql.FIND_BY_ID_STATEMENT;
import static nextstep.repository.ThemeJdbcSql.UPDATE_STATEMENT;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import nextstep.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


public class ThemeRepositoryImpl implements ThemeRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ThemeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Theme save(Theme theme) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("desc", theme.getDescription());
        parameters.put("name", theme.getName());
        parameters.put("price", theme.getPrice());
        long id = new SimpleJdbcInsert(jdbcTemplate).withTableName("THEME")
                .usingGeneratedKeyColumns("id").executeAndReturnKey(parameters).longValue();

        return Theme.createTheme(theme, id);
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_STATEMENT,
                (rs, rowNum) ->
                {
                    Theme theme = Theme.builder()
                            .price(rs.getInt("price"))
                            .name(rs.getString("name"))
                            .description(rs.getString("desc")).build();
                    Theme.createTheme(theme, rs.getLong("id"));
                    return Theme.createTheme(theme,id);
                }, id).stream().findAny();

    }

    @Override
    public int update(Theme theme) {
        return jdbcTemplate.update(UPDATE_STATEMENT,
                theme.getName(),
                theme.getDescription(), theme.getPrice(), theme.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_STATEMENT, id);
    }
}
