package nextstep.repository.theme;

import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import static nextstep.exception.ErrorCode.DUPLICATED_THEME_EXISTS;
import static nextstep.exception.ErrorCode.THEME_NOT_FOUND;

@Repository
public class JdbcThemeRepository extends ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Theme> actorRowMapper = (resultSet, rowNum) -> Theme.from(resultSet);

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(String name, String desc, Integer price) {
        validateTheme(name);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = con -> getThemePreparedStatement(con, name, desc, price);
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void validateTheme(String name) {
        Integer count = jdbcTemplate.queryForObject(CHECK_DUPLICATION_SQL, Integer.class, name);
        if (count > 0) {
            throw new ReservationException(DUPLICATED_THEME_EXISTS);
        }
    }

    @Override
    public Long save(Theme theme) {
        return this.save(theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public Theme findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, actorRowMapper, id);
        } catch (DataAccessException e) {
            throw new ReservationException(THEME_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        if (updatedRows == 0) {
            throw new ReservationException(THEME_NOT_FOUND);
        }
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    @Override
    public void dropTable() {
        jdbcTemplate.execute(DROP_TABLE_SQL);
    }
}
