package nextstep.repository;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;

import java.sql.*;

public class ConsoleReservationRepo implements ReservationRepo {
    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    private static final String USER = "sa";
    private static final String PW = "";

    public Reservation findById(long id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM reservation WHERE id = ?;";
        Reservation reservation = null;

        try {
            con = DriverManager.getConnection(URL, USER, PW);
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                reservation = new Reservation(
                        rs.getLong(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getTime(3).toLocalTime(),
                        rs.getString(4),
                        new Theme(
                                rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return reservation;
    }

    public long add(Reservation reservation) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        Long id = null;

        try {
            con = DriverManager.getConnection(URL, USER, PW);
            ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    public int delete(long id) {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM reservation WHERE id = ?;";
        int result = 0;

        try {
            con = DriverManager.getConnection(URL, USER, PW);
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public int countWhenDateAndTimeMatch(Date date, Time time) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        int count = 0;

        try {
            con = DriverManager.getConnection(URL, USER, PW);
            ps = con.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setTime(2, time);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    public int reset() {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "TRUNCATE TABLE reservation;";
        int result = 0;

        try {
            con = DriverManager.getConnection(URL, USER, PW);
            ps = con.prepareStatement(sql);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}