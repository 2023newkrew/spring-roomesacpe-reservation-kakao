package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.sql.*;

public class ReservationConsoleRepository implements ReservationRepository {

    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private void close(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    @Override
    public Long addReservation(Reservation reservation) {
        Connection con = getConnection();
        Long id = null;
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
            id = rs.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return id;
    }

    @Override
    public int checkSchedule(String date, String time) {
        int count = 0;
        Connection con = getConnection();
        try {
            String sql = "SELECT COUNT(*) FROM reservation WHERE `date` = ? AND `time` = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return count;
    }

    @Override
    public Reservation findReservation(Long id) {
        Reservation reservation = null;
        Connection con = getConnection();
        try {
            String sql = "SELECT * FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = new Reservation(
                        rs.getLong("id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getString("name"),
null
//                        new Theme(
//                                rs.getString("theme_name"),
//                                rs.getString("theme_desc"),
//                                rs.getInt("theme_price")
//                        )
                );
            } else {
                throw new IllegalArgumentException("데이터 없음 비상비상");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return reservation;
    }

    @Override
    public int removeReservation(Long id) {
        int count = 0;
        Connection con = getConnection();
        try {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        close(con);
        return count;
    }

}