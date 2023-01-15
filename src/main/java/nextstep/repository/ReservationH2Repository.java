package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static nextstep.repository.ConnectionHandler.closeConnection;
import static nextstep.repository.ConnectionHandler.getConnection;

public class ReservationH2Repository implements ReservationRepository{

    @Override
    public Reservation add(Reservation reservation) {
        Connection con = getConnection();

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                Long id = rs.getLong(1);
                reservation.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }

        return reservation;
    }

    @Override
    public Reservation findById(Long id) throws ReservationNotFoundException {
        Connection con = getConnection();
        Reservation result;

        try {
            String sql = "SELECT r.*, t.* FROM reservation r JOIN theme t ON r.theme_id = t.id where r.id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long reservationId = rs.getLong("id");
                LocalDate reservationDate = rs.getDate(2).toLocalDate();
                LocalTime reservationTime = rs.getTime(3).toLocalTime();
                String reservationName = rs.getString(4);
                Long themeId = rs.getLong(6);
                String themeName = rs.getString(7);
                String themeDesc = rs.getString(8);
                Integer themePrice = rs.getInt(9);
                Theme reservationTheme = new Theme(themeId, themeName, themeDesc, themePrice);

                result = new Reservation(reservationId, reservationDate, reservationTime, reservationName, reservationTheme);
            } else {
                throw new ReservationNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }

        return result;
    }

    @Override
    public void deleteById(Long id) {
        Connection con = getConnection();

        try {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }

    @Override
    public boolean hasReservationAt(LocalDate date, int hour) {
        Connection con = getConnection();

        try {
            String sql = "SELECT count(*) AS cnt FROM reservation WHERE date = ? AND HOUR(time) = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setInt(2, hour);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int cnt = rs.getInt("cnt");
            return cnt >= 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }

    public boolean existsByThemeId(Long themeId) {
        Connection con = getConnection();

        try {
            String sql = "SELECT count(*) AS cnt FROM reservation WHERE theme_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, themeId);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int cnt = rs.getInt("cnt");
            return cnt >= 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con);
        }
    }

}
