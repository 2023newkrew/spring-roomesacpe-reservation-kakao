package nextstep.reservation.repository;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ReservationStatementCreator {

    private static final String SELECT_BY_DATE_AND_TIME_SQL = "SELECT * FROM reservation WHERE date = ? AND time = ? LIMIT 1";

    private static final String INSERT_SQL = "INSERT INTO reservation(date,time,name,theme_name,theme_desc,theme_price) VALUES(?,?,?,?,?,?)";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM reservation WHERE id = ?";

    private static final String DELETE_BY_ID_SQL = "DELETE FROM reservation WHERE id = ?";

    public PreparedStatement createSelectByDateAndTimeStatement(
            Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_BY_DATE_AND_TIME_SQL);
        LocalDate date = reservation.getDate();
        LocalTime time = reservation.getTime();
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));

        return ps;
    }

    public PreparedStatement createInsertStatement(
            Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
        setReservation(ps, reservation);

        return ps;
    }

    private void setReservation(PreparedStatement ps, Reservation reservation) throws SQLException {
        LocalDate date = reservation.getDate();
        LocalTime time = reservation.getTime();
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));
        ps.setString(3, reservation.getName());
        setTheme(ps, reservation.getTheme());
    }

    private void setTheme(PreparedStatement ps, Theme theme) throws SQLException {
        ps.setString(4, theme.getName());
        ps.setString(5, theme.getDesc());
        ps.setInt(6, theme.getPrice());
    }

    public PreparedStatement createSelectByIdStatement(Connection connection, Long id) throws SQLException {
        return createByIdStatement(connection, SELECT_BY_ID_SQL, id);
    }

    public PreparedStatement createDeleteByIdStatement(Connection connection, Long id) throws SQLException {
        return createByIdStatement(connection, DELETE_BY_ID_SQL, id);
    }

    private PreparedStatement createByIdStatement(
            Connection connection, String sql, Long id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }
}