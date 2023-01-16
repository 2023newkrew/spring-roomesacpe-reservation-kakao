package nextstep.etc.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultSetParser {

    public Long parseKey(ResultSet resultSet) throws SQLException {
        resultSet.next();

        return resultSet.getLong(1);
    }
}
