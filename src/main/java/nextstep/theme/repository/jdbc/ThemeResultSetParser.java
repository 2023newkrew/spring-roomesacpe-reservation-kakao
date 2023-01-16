package nextstep.theme.repository.jdbc;

import nextstep.etc.jdbc.ResultSetParser;
import nextstep.theme.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeResultSetParser extends ResultSetParser {


    public Theme parseTheme(ResultSet resultSet) throws SQLException {
        if (getRows(resultSet) == 0) {
            return null;
        }

        return new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
    }
}
