package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;

import java.sql.*;


public class ConsoleReservationRepository {

    private final String url = "jdbc:h2:~/workspace/kakao/spring-roomesacpe-reservation-kakao/room-escape";
    private final String user = "sa";

    private final String password = "";

    public ConsoleReservationRepository() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement pstmt = null;
            String sql = "TRUNCATE TABLE reservation";

            pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void add(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO reservation SET date = ?, time = ?, name = ?, theme_name = ?, theme_desc = ?, theme_price = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(reservation.getDate()));
            pstmt.setTime(2, Time.valueOf(reservation.getTime()));
            pstmt.setString(3, reservation.getName());
            pstmt.setString(4, reservation.getTheme().getName());
            pstmt.setString(5, reservation.getTheme().getDesc());
            pstmt.setInt(6, reservation.getTheme().getPrice());
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DuplicateReservationException();
        }
    }

    public Reservation findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, "")) {
            PreparedStatement pstmt = null;
            String sql = "SELECT * FROM reservation WHERE id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                return Reservation.builder()
                        .id(rs.getLong("id"))
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .name(rs.getString("name"))
                        .theme(Theme.builder()
                                .name(rs.getString("theme_name"))
                                .desc(rs.getString("theme_desc"))
                                .price(rs.getInt("theme_price"))
                                .build())
                        .build();
            }
            return null;
        }

        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void remove(final Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement pstmt = null;

            String sql = "DELETE FROM reservation WHERE id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
