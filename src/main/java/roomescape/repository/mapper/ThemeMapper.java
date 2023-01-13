package roomescape.repository.mapper;

import roomescape.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper {
    private ThemeMapper() {
    }

    public static Theme mapToTheme(ResultSet resultSet) throws SQLException {
        return new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getInt("price")
        );
    }
}
