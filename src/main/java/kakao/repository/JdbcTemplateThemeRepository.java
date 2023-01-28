package kakao.repository;

import kakao.controller.request.ThemeRequest;
import kakao.controller.response.ThemeResponse;
import kakao.exception.ThemeNotFoundException;
import kakao.model.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateThemeRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(ThemeRequest themeRequest) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(Theme.Column.NAME, themeRequest.getName())
                .addValue(Theme.Column.DESC, themeRequest.getDesc())
                .addValue(Theme.Column.PRICE, themeRequest.getPrice());

        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM theme WHERE id=?", themeResponseRowMapper(), id).stream().findAny();
    }

    @Override
    public Optional<Theme> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM theme WHERE name=?", themeResponseRowMapper(), name).stream().findAny();
    }

    @Override
    public List<Theme> findAll() {
        return jdbcTemplate.query("SELECT * FROM theme", themeResponseRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        if (findById(id).isPresent()) {
            jdbcTemplate.update("DELETE FROM theme WHERE id=?", id);
            return;
        }
        throw new ThemeNotFoundException();
    }

    private RowMapper<Theme> themeResponseRowMapper() {
        return (resultSet, rowNumber) -> {
            Long id = resultSet.getLong(Theme.Column.ID);
            String name = resultSet.getString(Theme.Column.NAME);
            String desc = resultSet.getString(Theme.Column.DESC);
            Integer price = resultSet.getInt(Theme.Column.PRICE);

            return new Theme(id, name, desc, price);
        };
    }
}
