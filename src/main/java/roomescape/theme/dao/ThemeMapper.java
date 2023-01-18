package roomescape.theme.dao;

import org.springframework.jdbc.core.RowMapper;
import roomescape.theme.domain.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper implements RowMapper<Theme> {
    @Override
    public Theme mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
        );
    }
}
