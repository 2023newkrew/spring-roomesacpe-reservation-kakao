package nextstep.domain.theme.repository;

import nextstep.domain.theme.Theme;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    private final RowMapper<Theme> themeRowMapper = (rs, rowNum) -> new Theme(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("desc"),
            rs.getInt("price")
    );

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
        try {
            return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_NAME, themeRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Theme> findAll(int page, int size) {
        return jdbcTemplate.query(SELECT_ALL, themeRowMapper, size, page);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public void update(Theme theme) {
        jdbcTemplate.update(UPDATE_BY_ID, theme.getName(), theme.getDesc(), theme.getPrice(), theme.getId());
    }

}
