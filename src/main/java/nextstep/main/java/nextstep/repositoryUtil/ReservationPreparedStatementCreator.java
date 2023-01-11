package nextstep.main.java.nextstep.repositoryUtil;

import nextstep.main.java.nextstep.domain.Reservation;

import java.sql.*;

public class ReservationPreparedStatementCreator {
    public static PreparedStatement insertReservationPreparedStatement(Connection con, Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
        preparedStatement.setDate(1, Date.valueOf(reservation.getDate()));
        preparedStatement.setTime(2, Time.valueOf(reservation.getTime()));
        preparedStatement.setString(3, reservation.getName());
        preparedStatement.setString(4, reservation.getTheme()
                .getName());
        preparedStatement.setString(5, reservation.getTheme()
                .getDesc());
        preparedStatement.setInt(6, reservation.getTheme()
                .getPrice());
        return preparedStatement;
    }
}
