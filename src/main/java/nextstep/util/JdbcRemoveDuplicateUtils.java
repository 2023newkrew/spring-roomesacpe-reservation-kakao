package nextstep.util;

import nextstep.model.Reservation;
import nextstep.model.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class JdbcRemoveDuplicateUtils {
    public static void setReservationToStatement(PreparedStatement ps, Reservation reservation) throws SQLException {
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setString(4, reservation.getTheme().getName());
        ps.setString(5, reservation.getTheme().getDesc());
        ps.setInt(6, reservation.getTheme().getPrice());
    }

    public static Reservation getReservationFromResultSet(ResultSet rs, Long id) throws SQLException {
        LocalDate date = rs.getDate("date").toLocalDate();
        LocalTime time = rs.getTime("time").toLocalTime();
        String name = rs.getString("name");
        String themeName = rs.getString("theme_name");
        String themeDesc = rs.getString("theme_desc");
        Integer themePrice = rs.getInt("theme_price");
        return new Reservation(id, date, time, name, new Theme(themeName, themeDesc, themePrice));
    }
}
