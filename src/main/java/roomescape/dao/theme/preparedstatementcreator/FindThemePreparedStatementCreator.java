package roomescape.dao.theme.preparedstatementcreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class FindThemePreparedStatementCreator implements PreparedStatementCreator {

    private static final String SQL =
            "SELECT * FROM theme WHERE id=?;";

    private final long id;

    public FindThemePreparedStatementCreator(long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SQL, new String[]{"id"});
        ps.setLong(1, id);
        return ps;
    }

}
