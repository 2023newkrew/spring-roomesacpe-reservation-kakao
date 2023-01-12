package nextstep.dao;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThemeJdbcTemplateDAO implements ThemeDAO {
    private final JdbcTemplate jdbcTemplate;

    public ThemeJdbcTemplateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator insertPreparedStatementCreator = ThemeDAO.getInsertPreparedStatementCreator(theme);

        jdbcTemplate.update(insertPreparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Theme> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, THEME_ROW_MAPPER);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }
}
