package nextstep.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import nextstep.model.Reservation;

public class ReservationConverter {
    public static void set(PreparedStatement ps, Reservation reservation) throws SQLException {
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setString(4, reservation.getTheme().getName());
        ps.setString(5, reservation.getTheme().getDesc());
        ps.setInt(6, reservation.getTheme().getPrice());
    }
}
