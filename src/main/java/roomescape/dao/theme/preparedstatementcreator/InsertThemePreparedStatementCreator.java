package roomescape.dao.theme.preparedstatementcreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import roomescape.dto.Theme;

public class InsertThemePreparedStatementCreator implements PreparedStatementCreator {

    private static final String SQL =
            "INSERT INTO theme(name, desc, price) "
                    + "VALUES (?, ?, ?);";

    private final Theme theme;

    public InsertThemePreparedStatementCreator(Theme theme) {
        this.theme = theme;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SQL, new String[]{"id"});
        ps.setString(1, theme.getName());
        ps.setString(2, theme.getDesc());
        ps.setInt(3, theme.getPrice());
        return ps;
    }
}
