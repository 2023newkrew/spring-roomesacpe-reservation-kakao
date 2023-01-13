package roomescape.dao.theme.preparedstatementcreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class ListThemePreparedStatementCreator implements PreparedStatementCreator {

    private static final String SQL =
            "SELECT * FROM theme";

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        return con.prepareStatement(SQL, new String[]{"id"});
    }
}
