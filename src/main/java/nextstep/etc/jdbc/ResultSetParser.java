package nextstep.etc.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultSetParser {

    public Boolean existsRow(ResultSet resultSet) throws SQLException {
        return getRows(resultSet) > 0;
    }

    public Long parseKey(ResultSet resultSet) throws SQLException {
        resultSet.next();

        return resultSet.getLong(1);
    }

    protected int getRows(ResultSet resultSet) throws SQLException {
        resultSet.last();

        return resultSet.getRow();
    }
}
