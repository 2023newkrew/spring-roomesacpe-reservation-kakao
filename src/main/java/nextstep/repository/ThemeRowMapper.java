package nextstep.repository;

import nextstep.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ThemeRowMapper {

    private ThemeRowMapper() {}

    public static Theme mapRow(ResultSet resultSet, int rownum) throws SQLException {
        return Theme.of(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
    }
}
