package nextstep.main.java.nextstep.repository.theme;

import nextstep.main.java.nextstep.domain.theme.Theme;
import nextstep.main.java.nextstep.domain.theme.ThemeCreateRequestDto;
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
    public Long save(ThemeCreateRequestDto request) {
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(request)).longValue();
    }

    @Override
    public Optional<Theme> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Theme> findByName(String name) {
        String sql = "SELECT * FROM theme WHERE name = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, themeRowMapper, name));
    }

    @Override
    public List<Theme> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
