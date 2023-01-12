package roomescape.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

public class ReservationJdbcRepository {

    public void addReservation(Reservation reservation) {
        // 드라이버 연결
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            System.out.println("정상적으로 연결되었습니다.");

            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Reservation> findById(Long reservationId) {
        // 드라이버 연결
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            System.out.println("정상적으로 연결되었습니다.");

            String sql = "SELECT * FROM reservation WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }

            final Theme theme = new Theme(rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7));

            final Reservation reservation = new Reservation(
                    rs.getLong(1),
                    rs.getDate(2).toLocalDate(),
                    rs.getTime(3).toLocalTime(),
                    rs.getString(4),
                    theme
            );

            return Optional.of(reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(Long reservationId) {
        // 드라이버 연결
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            System.out.println("정상적으로 연결되었습니다.");
            String sql = "DELETE FROM reservation WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);

            int rowNum = ps.executeUpdate();

            return rowNum > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
