package nextstep.repository;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;

import java.sql.*;
import java.util.Optional;

public class ConsoleReservationRepository implements ReservationRepository {

    public Optional<Reservation> findById(long id) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reservation reservation = null;

        try {
            String sql = "SELECT * FROM reservation WHERE id = ?;";
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
                                rs.getInt(7)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ConnectionManager.closeAll(rs, ps, con);

        return Optional.ofNullable(reservation);
    }

    public long add(Reservation reservation) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = null;

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
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
        }

        ConnectionManager.closeAll(rs, ps, con);

        return id;
    }

    public int delete(long id) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement ps = null;
        int result = 0;

        try {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        ConnectionManager.closeAll(ps, con);

        return result;
    }

    public int countByDateAndTime(Date date, Time time) {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;

        try {
            String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
            ps = con.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setTime(2, time);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ConnectionManager.closeAll(rs, ps, con);

        return result;
    }

    public void deleteAll() {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement ps = null;

        try {
            String sql = "TRUNCATE TABLE reservation;";
            ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ConnectionManager.closeAll(ps, con);
    }

}