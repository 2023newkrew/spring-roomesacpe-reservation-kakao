package nextstep.repository.reservation;

import nextstep.domain.theme.Theme;
import nextstep.domain.reservation.Reservation;
import nextstep.repository.ConnectionManager;

import java.sql.*;
import java.util.Optional;

public class ConsoleReservationRepository implements ReservationRepository {

    @Override
    public Optional<Reservation> findById(long id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reservation reservation = null;

        try {
            String sql = "SELECT r.id, r.date, r.time, r.name, t.id, t.name, t.desc, t.price" +
                    " FROM reservation r" +
                    " INNER JOIN theme t ON r.theme_id = t.id" +
                    " WHERE r.id = ?;";
            con = ConnectionManager.getConnection();
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
                                rs.getLong(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getInt(8)
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeAll(rs, ps, con);
        }

        return Optional.ofNullable(reservation);
    }

    @Override
    public long add(Reservation reservation) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = null;

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeAll(rs, ps, con);
        }

        return id;
    }

    @Override
    public int delete(long id) {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeAll(ps, con);
        }

        return result;
    }

    @Override
    public int countByDateAndTime(Date date, Time time) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;

        try {
            String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
            con = ConnectionManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setTime(2, time);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeAll(rs, ps, con);
        }

        return result;
    }
}