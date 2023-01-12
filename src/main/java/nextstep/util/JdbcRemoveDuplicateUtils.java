package nextstep.util;

import nextstep.model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class JdbcRemoveDuplicateUtils {
    public static void setReservationToStatement(PreparedStatement ps, Reservation reservation) throws SQLException {
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setLong(4, reservation.getThemeId());
    }

    public static Reservation getReservationFromResultSet(ResultSet rs, Long id) throws SQLException {
        LocalDate date = rs.getDate("date").toLocalDate();
        LocalTime time = rs.getTime("time").toLocalTime();
        String name = rs.getString("name");
        Long themeId = rs.getLong("theme_id");
        return new Reservation(id, date, time, name, themeId);
    }
}
