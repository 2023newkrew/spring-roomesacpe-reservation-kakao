package nextstep;

import java.sql.*;

public class ReservationJdbcRepository {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "";

    public Long save(Reservation reservation) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
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
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "SELECT * FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
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

    public int delete(Long reservationId) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
