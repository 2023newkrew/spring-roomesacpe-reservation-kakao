package roomescape.dao.theme.preparedstatementcreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class RemoveThemePreparedStatementCreator implements PreparedStatementCreator {

    private static final String SQL =
            "DELETE FROM theme WHERE id=?;";

    private final Long id;

    public RemoveThemePreparedStatementCreator(Long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SQL, new String[]{"id"});
        ps.setLong(1, id);
        return ps;
    }
}
