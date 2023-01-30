package roomescape.reservation.repository.common;

import org.springframework.jdbc.core.RowMapper;
import roomescape.reservation.domain.Theme;

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
