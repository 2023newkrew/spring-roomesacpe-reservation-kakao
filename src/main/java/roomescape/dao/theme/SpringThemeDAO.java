package roomescape.dao.theme;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dao.DAOResult;
import roomescape.dao.theme.preparedstatementcreator.ExistThemeIdPreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ExistThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.FindThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.RemoveThemePreparedStatementCreator;
import roomescape.dto.Theme;

@Repository
public class SpringThemeDAO implements ThemeDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpringThemeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Boolean exist(Theme theme) {
        try {
            List<Boolean> result = jdbcTemplate.query(
                    new ExistThemePreparedStatementCreator(theme), existRowMapper);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean existId(long id) {
        try {
            List<Boolean> result = jdbcTemplate.query(
                    new ExistThemeIdPreparedStatementCreator(id), existRowMapper);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long create(Theme theme) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new InsertThemePreparedStatementCreator(theme), keyHolder);
            return keyHolder.getKeyAs(Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Theme> list() {
        try {
            return jdbcTemplate.query(
                    new ListThemePreparedStatementCreator(), rowMapper);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(long id) {
        try {
            jdbcTemplate.update(
                    new RemoveThemePreparedStatementCreator(id));
        } catch (Exception ignored) {

        }
    }

    @Override
    public Theme find(long id) {
        try {
            List<Theme> result = jdbcTemplate.query(
                    new FindThemePreparedStatementCreator(id), rowMapper);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }
}
