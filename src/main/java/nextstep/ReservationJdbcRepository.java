package nextstep;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ReservationJdbcRepository {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public Long save(Reservation reservation) {
        Connection con;
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"})) {
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            return (long) ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findOneById(Long reservationId) {
        Connection con;
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        String sql = "SELECT * FROM reservation WHERE id = ?;";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, reservationId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getString("name"),
                    new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer delete(Long reservationId) {
        Connection con;
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        String sql = "DELETE FROM reservation WHERE id = ?;";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, reservationId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
