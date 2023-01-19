package nextstep.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.model.Reservation;
import nextstep.model.Theme;
import org.springframework.jdbc.core.RowMapper;

class ReservationRowMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("reservation_id");
        LocalDate date = rs.getDate("reservation_date").toLocalDate();
        LocalTime time = rs.getTime("reservation_time").toLocalTime();
        String name = rs.getString("reservation_name");
        Long themeId = rs.getLong("theme_id");
        String themeName = rs.getString("theme_name");
        String themeDesc = rs.getString("theme_desc");
        Integer themePrice = rs.getInt("theme_price");
        return new Reservation(id, date, time, name, new Theme(themeId, themeName, themeDesc, themePrice));
    }
}
