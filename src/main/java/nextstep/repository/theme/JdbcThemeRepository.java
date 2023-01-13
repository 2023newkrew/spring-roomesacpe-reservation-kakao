package nextstep.repository.theme;

import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcThemeRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Theme> themeActorRowMapper = (resultSet, rowNum) ->
            Theme.from(resultSet);

    @Override
    public Theme findByThemeId(Long id) {
        return jdbcTemplate.queryForObject(findSql, themeActorRowMapper, id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(deleteByIdSql, id);
    }

    @Override
    public Long save(Theme theme) {
        validateTheme(theme);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connection) ->
                getReservationPreparedStatement(connection, theme);

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void validateTheme(Theme theme) {
        Integer count = jdbcTemplate.queryForObject(checkDuplicationSql, Integer.class, theme.getName());
        if (count > 0) throw new IllegalArgumentException("이름이 같은 테마가 이미 존재합니다.");
    }
}
