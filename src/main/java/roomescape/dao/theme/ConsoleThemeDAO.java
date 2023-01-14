package roomescape.dao.theme;

import java.util.List;
import roomescape.dao.ConnectionDAOManager;
import roomescape.dao.DAOResult;
import roomescape.dao.theme.preparedstatementcreator.ExistThemeIdPreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ExistThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.FindThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.RemoveThemePreparedStatementCreator;
import roomescape.dto.Theme;

public class ConsoleThemeDAO implements ThemeDAO {

    private final ConnectionDAOManager connectionDAOManager;

    public ConsoleThemeDAO(String url, String user, String password) {
        connectionDAOManager = new ConnectionDAOManager(url, user, password);
    }

    @Override
    public Boolean exist(Theme theme) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistThemePreparedStatementCreator(theme), existRowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public Boolean existId(long id) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistThemeIdPreparedStatementCreator(id), existRowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public Long create(Theme theme) {
        List<Long> result = connectionDAOManager.updateAndGetKey(
                new InsertThemePreparedStatementCreator(theme), "id", Long.class);
        return DAOResult.getResult(result);
    }

    @Override
    public List<Theme> list() {
        return connectionDAOManager.query(
                new ListThemePreparedStatementCreator(), rowMapper);
    }

    @Override
    public void remove(long id) {
        connectionDAOManager.update(
                new RemoveThemePreparedStatementCreator(id));
    }

    @Override
    public Theme find(long id) {
        List<Theme> result = connectionDAOManager.query(
                new FindThemePreparedStatementCreator(id), rowMapper);
        return DAOResult.getResult(result);
    }
}
