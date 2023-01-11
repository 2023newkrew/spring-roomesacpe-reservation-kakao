package nextstep.main.java.nextstep.mvc.repository.theme;

import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.request.ThemeUpdateRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcThemeRepository implements ThemeRepository{
    private static final String TABLE_NAME = "theme";
    private static final String PRIMARY_KEY = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(PRIMARY_KEY);
    }

    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getInt("price")
    );

    @Override
    public Long save(ThemeCreateRequest request) {
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(request)).longValue();
    }

    @Override
    public Optional<Theme> findById(long id) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, themeRowMapper, id));
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Long id, ThemeUpdateRequest request) {
        String sql = "UPDATE theme " +
                "SET name = ?, desc = ?, price = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getDesc(), request.getPrice(), id);
    }

    @Override
    public Boolean existsById(Long id) {
        String sql = "SELECT EXISTS(SELECT * FROM theme WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql ,new Object[] {id}, Boolean.class);
    }
}
