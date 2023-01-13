package nextstep.etc.util;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class StatementCreator {

    private static final String SELECT_BY_DATE_AND_TIME_SQL = "SELECT * FROM reservation WHERE date = ? AND time = ? LIMIT 1";

    private static final String INSERT_SQL = "INSERT INTO reservation(date,time,name,theme_name,theme_desc,theme_price) VALUES(?,?,?,?,?,?)";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM reservation WHERE id = ?";

    private static final String DELETE_BY_ID_SQL = "DELETE FROM reservation WHERE id = ?";

    public static PreparedStatement createSelectByDateAndTimeStatement(
            Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_BY_DATE_AND_TIME_SQL);
        LocalDate date = reservation.getDate();
        LocalTime time = reservation.getTime();
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));

        return ps;
    }

    public static PreparedStatement createInsertStatement(
            Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
        setReservation(ps, reservation);

        return ps;
    }

    private static void setReservation(PreparedStatement ps, Reservation reservation) throws SQLException {
        LocalDate date = reservation.getDate();
        LocalTime time = reservation.getTime();
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ps.setString(3, reservation.getName());
        setTheme(ps, reservation.getTheme());
    }

    private static void setTheme(PreparedStatement ps, Theme theme) throws SQLException {
        ps.setString(4, theme.getName());
        ps.setString(5, theme.getDesc());
        ps.setInt(6, theme.getPrice());
    }

    public static PreparedStatement createSelectByIdStatement(Connection connection, Long id) throws SQLException {
        return createByIdStatement(connection, SELECT_BY_ID_SQL, id);
    }

    public static PreparedStatement createDeleteByIdStatement(Connection connection, Long id) throws SQLException {
        return createByIdStatement(connection, DELETE_BY_ID_SQL, id);
    }

    private static PreparedStatement createByIdStatement(
            Connection connection, String sql, Long id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }
}