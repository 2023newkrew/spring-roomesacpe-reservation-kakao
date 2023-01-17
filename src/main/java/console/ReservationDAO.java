package console;

import web.domain.Reservation;
import web.dto.request.ReservationRequestDTO;

import java.sql.*;
import java.util.Optional;

public class ReservationDAO {

    public Reservation add(ReservationRequestDTO reservationRequestDTO) {
        try(Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            System.out.println("정상적으로 연결되었습니다.");
            Reservation reservation = reservationRequestDTO.toEntity();
            String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setInt(4, reservation.getThemeId().intValue());
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            Long id = resultSet.getLong("id");

            return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getThemeId());
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Optional<Reservation> findById(Long reservationId) {
        try(Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {

            System.out.println("정상적으로 연결되었습니다.");
            String sql = "SELECT * FROM reservation WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }

            final Reservation reservation = new Reservation(
                    rs.getLong(1),
                    rs.getDate(2).toLocalDate(),
                    rs.getTime(3).toLocalTime(),
                    rs.getString(4),
                    rs.getLong(5)
            );

            return Optional.of(reservation);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean deleteById(Long reservationId) {
        try(Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {

            System.out.println("정상적으로 연결되었습니다.");
            String sql = "DELETE FROM reservation WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);

            int rowNum = ps.executeUpdate();
            con.close();

            return rowNum > 0;
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
