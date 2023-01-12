package nextstep;

import reservation.domain.Reservation;

import java.sql.*;

public class ConsoleRepository {
    private final static ConnectionManager connectionManager = new ConnectionManager();

    public void addReservation(Reservation reservation) {
        Connection con = connectionManager.get();

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new RuntimeException(e);
        }

        connectionManager.close();
    }

    public Reservation getReservation(Long id) {
        Connection con = connectionManager.get();
        Reservation reservation = null;

        try {
            String sql = "SELECT date, time, name, theme_id FROM reservation WHERE id = ?;";

            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reservation = new Reservation(
                        id,
                        rs.getDate(1).toLocalDate(),
                        rs.getTime(2).toLocalTime(),
                        rs.getString(3),
                        rs.getLong(4)
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new RuntimeException(e);
        }

        connectionManager.close();

        if (reservation == null) {
            throw new RuntimeException();
        }
        return reservation;
    }

    public int deleteReservation(Long id) {
        Connection con = connectionManager.get();
        int row;

        try {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            row = ps.executeUpdate();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new RuntimeException(e);
        }

        connectionManager.close();
        return row;
    }
}