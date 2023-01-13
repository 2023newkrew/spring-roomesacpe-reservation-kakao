package roomescape.dao.theme.preparedstatementcreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import roomescape.dto.Theme;

public class ExistThemePreparedStatementCreator implements PreparedStatementCreator {

    String SQL =
            "SELECT EXISTS ( SELECT * FROM theme WHERE name = ?) as result;";

    private final String name;

    public ExistThemePreparedStatementCreator(Theme theme) {
        this(theme.getName());
    }

    public ExistThemePreparedStatementCreator(String name) {
        this.name = name;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(SQL, new String[]{"id"});
        ps.setString(1, name);
        return ps;
    }
}
