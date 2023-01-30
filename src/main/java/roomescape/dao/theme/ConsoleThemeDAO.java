package roomescape.dao.theme;

import java.util.List;
import roomescape.connection.ConnectionManager;
import roomescape.dao.theme.preparedstatementcreator.ExistThemeIdPreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ExistThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.FindThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.InsertThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.ListThemePreparedStatementCreator;
import roomescape.dao.theme.preparedstatementcreator.RemoveThemePreparedStatementCreator;
import roomescape.dto.Theme;

public class ConsoleThemeDAO implements ThemeDAO {

    private final ConnectionManager connectionManager;

    public ConsoleThemeDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Boolean exist(Theme theme) {
        try {
            return connectionManager.query(
                    new ExistThemePreparedStatementCreator(theme),
                    existResultSetExtractor);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean existId(long id) {
        try {
            return connectionManager.query(
                    new ExistThemeIdPreparedStatementCreator(id),
                    existResultSetExtractor);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long create(Theme theme) {
        try {
            return connectionManager.updateAndGetKey(
                    new InsertThemePreparedStatementCreator(theme),
                    "id", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Theme> list() {
        try {
            return connectionManager.query(
                    new ListThemePreparedStatementCreator(), themeRowMapper);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(long id) {
        try {
            connectionManager.update(
                    new RemoveThemePreparedStatementCreator(id));
        } catch (Exception ignored) {

        }
    }

    @Override
    public Theme find(long id) {
        try {
            return connectionManager.query(
                    new FindThemePreparedStatementCreator(id),
                    themeResultSetExtractor);
        } catch (Exception e) {
            return null;
        }
    }
}
