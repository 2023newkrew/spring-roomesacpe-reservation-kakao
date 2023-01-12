package kakao.repository;

import domain.Theme;
import kakao.error.ErrorCode;
import kakao.error.exception.RecordNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ThemeJDBCRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ThemeJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) ->
            new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("desc"),
                    resultSet.getInt("price")
            );

    public long save(Theme theme) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(theme);

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public Theme findById(long id) {
        String SELECT_SQL = "select * from theme where id=?";

        try {
            return jdbcTemplate.queryForObject(SELECT_SQL, themeRowMapper, id);
        } catch (DataAccessException e) {
            throw new RecordNotFoundException(ErrorCode.THEME_NOT_FOUND, e);
        }
    }

    public int updateName(long id, String updateName) {
        if (findByName(updateName).size() > 0)
            return 0;

        String UPDATE_SQL = "update theme set name=? where id=?";

        return jdbcTemplate.update(UPDATE_SQL, updateName, id);
    }

    public List<Theme> findByName(String name) {
        String SELECT_SQL = "select * from theme where name=?";

        return jdbcTemplate.query(SELECT_SQL, themeRowMapper, name);
    }

    public int updateDesc(long id, String updateDesc) {
        String UPDATE_SQL = "update theme set desc=? where id=?";

        return jdbcTemplate.update(UPDATE_SQL, updateDesc, id);
    }

    public int updatePrice(long id, int updatePrice) {
        String UPDATE_SQL = "update theme set price=? where id=?";

        return jdbcTemplate.update(UPDATE_SQL, updatePrice, id);
    }

    public int delete(long id) {
        String DELETE_SQL = "delete theme where id=?";

        return jdbcTemplate.update(DELETE_SQL, id);
    }
}
