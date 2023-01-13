package roomescape.dao.theme;

import java.util.List;
import roomescape.dao.ConnectionDAOManager;
import roomescape.dao.theme.preparedstatementcreator.ExistThemeIdPreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ExistThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.RemoveThemePreparedStatementCreator;
import roomescape.dto.Theme;
import roomescape.exception.BadRequestException;

public class ConsoleThemeDAO implements ThemeDAO {

    private final ConnectionDAOManager connectionDAOManager;

    public ConsoleThemeDAO(String url, String user, String password) {
        connectionDAOManager = new ConnectionDAOManager(url, user, password);
    }

    private <T> void validateResult(List<T> result) {
        if (result.size() != 1) {
            throw new BadRequestException();
        }
    }

    @Override
    public boolean exist(Theme theme) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistThemePreparedStatementCreator(theme), existRowMapper);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public boolean existId(Long id) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistThemeIdPreparedStatementCreator(id), existRowMapper);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public Long create(Theme theme) {
        List<Long> result = connectionDAOManager.updateAndGetKey(
                new InsertThemePreparedStatementCreator(theme), "id", Long.class);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public List<Theme> list() {
        return connectionDAOManager.query(
                new ListThemePreparedStatementCreator(), rowMapper);
    }

    @Override
    public void remove(Long id) {
        connectionDAOManager.update(
                new RemoveThemePreparedStatementCreator(id));
    }
}
