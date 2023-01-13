package roomescape.dao.theme;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dto.Theme;

public class SpringThemeDAO extends ThemeDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpringThemeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean exist(Theme theme) {
        return false;
    }

    @Override
    public Long insert(Theme theme) {
        validate(theme);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new InsertThemePreparedStatementCreator(theme), keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public List<Theme> list() {
        return jdbcTemplate.query(
                new ListThemePreparedStatementCreator(), getRowMapper());
    }
}
