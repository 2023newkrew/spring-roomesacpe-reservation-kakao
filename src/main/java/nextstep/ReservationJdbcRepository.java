package nextstep;

import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;

import java.sql.*;

public class ReservationJdbcRepository {

    private Connection makeConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE", "sa", "");
            assert con != null;
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException | AssertionError e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    public void saveReservation(Reservation reservation) {
        try (Connection con = makeConnection()){
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation getReservation(Long id) {
        Reservation reservation = null;
        try (Connection con = makeConnection()){
            String sql = "SELECT date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?;";
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
                        new Theme(rs.getString(4),
                                rs.getString(5),
                                rs.getInt(6))
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new RuntimeException(e);
        }

        if (reservation == null) {
            throw new RuntimeException();
        }

        return reservation;
    }

    public int deleteReservation(Long id) {
        int row;
        try (Connection con = makeConnection()){
            String sql = "DELETE FROM reservation WHERE id = ?;";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            row = ps.executeUpdate();
            ps.close();
        } catch (SQLException | AssertionError e) {
            throw new RuntimeException(e);
        }
        return row;
    }
}
