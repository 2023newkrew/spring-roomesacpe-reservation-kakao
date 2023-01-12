package roomescape.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import roomescape.domain.Reservation;

public class ReservationJdbcRepository implements ReservationRepository {

    @Override
    public Long save(Reservation reservation) {
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
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reservation> findByDateAndTime(final LocalDate date, final LocalTime time) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            System.out.println("정상적으로 연결되었습니다.");

            String sql = "SELECT * FROM reservation WHERE date = (?) AND time = (?) LIMIT 1";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }

            final Reservation reservation = Reservation.from(rs);
            return Optional.of(reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

            final Reservation reservation = Reservation.from(rs);

            return Optional.of(reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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