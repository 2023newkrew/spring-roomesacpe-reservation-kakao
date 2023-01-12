package nextstep.main.java.nextstep.repositoryUtil;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.Theme;

import java.sql.*;

public class CustomPreparedStatementCreator {
    public static PreparedStatement insertReservationPreparedStatement(Connection con, Reservation reservation) throws SQLException {
        final String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
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

    public static PreparedStatement insertThemePreparedStatement(Connection con, Theme theme) throws SQLException {
        final String sql = "INSERT INTO theme (name, desc, price) VALUES(?, ?, ?);";
        PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
        preparedStatement.setString(1, theme.getName());
        preparedStatement.setString(2, theme.getDesc());
        preparedStatement.setInt(3, theme.getPrice());

        return preparedStatement;
    }

    public static PreparedStatement updateThemePreparedStatement(Connection con, Theme theme) throws SQLException {
        final String sql = "UPDATE theme SET (name, desc, price) = (?, ?, ?) WHERE id = ?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, theme.getName());
        preparedStatement.setString(2, theme.getDesc());
        preparedStatement.setInt(3, theme.getPrice());
        preparedStatement.setLong(4, theme.getId());

        return preparedStatement;
    }
}
