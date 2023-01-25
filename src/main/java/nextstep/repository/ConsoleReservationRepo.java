package nextstep.repository;

import nextstep.domain.reservation.Reservation;

import java.sql.*;

public class ConsoleReservationRepo implements ReservationRepo {
    public long save(Reservation reservation) {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        Long id = null;

        try {
            ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
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
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;

        String sql = "DELETE FROM reservation WHERE id = ?;";
        int result = 0;

        try {
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

    public Reservation findById(long id) {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM reservation WHERE id = ?;";
        Reservation reservation = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                reservation = new Reservation(
                        rs.getLong(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getTime(3).toLocalTime(),
                        rs.getString(4),
                        rs.getLong(5));
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

    public int findByDateAndTimeAndTheme(Date date, Time time, long themeId) {
        Connection con = ConsoleConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ? AND theme_id = ?";
        int count = 0;

        try {
            ps = con.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setTime(2, time);
            ps.setLong(3, themeId);
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
}