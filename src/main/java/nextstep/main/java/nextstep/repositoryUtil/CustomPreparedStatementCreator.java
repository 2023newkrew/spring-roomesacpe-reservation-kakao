package nextstep.main.java.nextstep.repositoryUtil;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.Theme;

import java.sql.*;

public class CustomPreparedStatementCreator {
    public static PreparedStatement insertReservationPreparedStatement(Connection con, Reservation reservation) throws SQLException {
        final String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
        preparedStatement.setDate(1, Date.valueOf(reservation.getDate()));
        preparedStatement.setTime(2, Time.valueOf(reservation.getTime()));
        preparedStatement.setString(3, reservation.getName());
        preparedStatement.setLong(4, reservation.getThemeId());
        return preparedStatement;
    }

    public static PreparedStatement insertThemePreparedStatement(Connection con, Theme theme) throws SQLException {
        final String sql = "INSERT INTO theme (name, desc, price) VALUES(?, ?, ?);";
        PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, theme.getName());
        preparedStatement.setString(2, theme.getDesc());
        preparedStatement.setInt(3, theme.getPrice());

        return preparedStatement;
    }
}
