package nextstep.console.dao;

import nextstep.domain.Reservation;
import nextstep.web.repository.ReservationRepository;

import java.sql.*;

public class ReservationDao implements ReservationRepository {
    public ReservationDao() {
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            System.out.println("정상적으로 연결되었습니다.");
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme()
                    .getName());
            ps.setString(5, reservation.getTheme()
                    .getDesc());
            ps.setInt(6, reservation.getTheme()
                    .getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Reservation findById(Long id) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            String sql = "SELECT * FROM reservation WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            return Reservation.from(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            String sql = "DELETE FROM reservation WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
