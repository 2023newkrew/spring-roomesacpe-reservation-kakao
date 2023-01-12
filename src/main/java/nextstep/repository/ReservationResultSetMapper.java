package nextstep.repository;

import nextstep.Reservation;
import nextstep.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ReservationResultSetMapper {
    public static Reservation mapRow(ResultSet rs) throws SQLException {
        return Reservation.of(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                Theme.of(
                        rs.getString("theme_name"),
                        rs.getString("theme_desc"),
                        rs.getInt("theme_price")
                )
        );
    }
}
