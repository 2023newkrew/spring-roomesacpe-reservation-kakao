package kakao.repository;

import java.util.List;
import javax.sql.DataSource;
import kakao.domain.Theme;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeJDBCRepository implements ThemeRepository{
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    public ThemeJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("THEME")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> Theme.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .desc(resultSet.getString("desc"))
            .price(resultSet.getInt("price"))
            .build();

    public Theme save(Theme theme) {
        try {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", theme.getName())
                    .addValue("desc", theme.getDesc())
                    .addValue("price", theme.getPrice());
            theme.setId(jdbcInsert.executeAndReturnKey(params).longValue());
            return theme;
        } catch (DuplicateKeyException e) {
            return null;
        }
    }

    public List<Theme> findAll() {
        String SELECT_SQL = "select * from theme";
        return jdbcTemplate.query(SELECT_SQL, themeRowMapper);
    }

    public Theme findById(Long id) {
        String SELECT_SQL = "select * from theme where id=?";
        try {
            return jdbcTemplate.queryForObject(SELECT_SQL, themeRowMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public int delete(Long id) {
        String DELETE_SQL = "delete from theme where id=?";
        return jdbcTemplate.update(DELETE_SQL, id);
    }
}
