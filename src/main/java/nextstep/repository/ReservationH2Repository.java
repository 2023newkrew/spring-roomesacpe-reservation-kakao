package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationH2Repository implements ReservationRepository{

    @Override
    public Reservation add(Reservation reservation) {
        Connection con = getConnection();

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
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
        Reservation result = null;

        try {
            String sql = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long reservationId = rs.getLong("id");
                LocalDate reservationDate = rs.getDate(2).toLocalDate();
                LocalTime reservationTime = rs.getTime(3).toLocalTime();
                String reservationName = rs.getString(4);
                String themeName = rs.getString(5);
                String themeDesc = rs.getString(6);
                Integer themePrice = rs.getInt(7);
                Theme reservationTheme = new Theme(themeName, themeDesc, themePrice);

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

    private Connection getConnection() {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }
}
