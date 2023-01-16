package roomescape.reservation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import roomescape.reservation.entity.ThemeReservation;

public class ThemeReservationRowMapper implements RowMapper<ThemeReservation> {

    @Override
    public ThemeReservation mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return ThemeReservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .themeId(rs.getLong("theme_id"))
                .themeName(rs.getString("theme.name"))
                .themeDesc(rs.getString("theme.desc"))
                .themePrice(rs.getInt("theme.price"))
                .build();
    }
}
