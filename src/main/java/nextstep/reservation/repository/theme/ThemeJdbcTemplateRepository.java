package nextstep.reservation.repository.theme;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import nextstep.reservation.entity.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeJdbcTemplateRepository implements ThemeRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Theme> themeRowMapper = (rs, rowNum)
            -> Theme.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .desc(rs.getString("desc"))
            .price(rs.getInt("price"))
            .build();

    public ThemeJdbcTemplateRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long add(Theme theme) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(theme);
        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public Optional<Theme> findById(Long id) {
        String sql = "SELECT * FROM theme WHERE id = (?)";
        return jdbcTemplate.query(sql, themeRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public Optional<Theme> findByName(String name) {
        String sql = "SELECT * FROM theme WHERE name = (?)";
        return jdbcTemplate.query(sql, themeRowMapper, name)
                .stream()
                .findAny();
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM theme WHERE id = (?)";
        return jdbcTemplate.update(sql, id) == 1;
    }
}