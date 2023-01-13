package nextstep.reservation.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;

import java.sql.*;


public class ReservationTraditionalRepository implements ReservationRepository{

    private final String url;
    private final String user;
    private final String password;

    public ReservationTraditionalRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Long add(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            String sql = "INSERT INTO reservation SET date = ?, time = ?, name = ?, theme_name = ?, theme_desc = ?, theme_price = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setDate(1, Date.valueOf(reservation.getDate()));
            pstmt.setTime(2, Time.valueOf(reservation.getTime()));
            pstmt.setString(3, reservation.getName());
            pstmt.setString(4, reservation.getTheme().getName());
            pstmt.setString(5, reservation.getTheme().getDesc());
            pstmt.setInt(6, reservation.getTheme().getPrice());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong("id");
            }
            return -1L;
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Optional<Reservation> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM reservation WHERE id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(Reservation.builder()
                        .id(rs.getLong("id"))
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .name(rs.getString("name"))
                        .theme(Theme.builder()
                                .name(rs.getString("theme_name"))
                                .desc(rs.getString("theme_desc"))
                                .price(rs.getInt("theme_price"))
                                .build())
                        .build()
                );
            }
            return Optional.empty();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Reservation> findAll() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM reservation";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            return getReservationsFromResultSet(rs);
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private static List<Reservation> getReservationsFromResultSet(ResultSet rs) throws SQLException{
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()) {
            reservations.add(getReservationFromResultSet(rs));
        }
        return reservations;
    }

    private static Reservation getReservationFromResultSet(ResultSet rs) throws SQLException {
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


    @Override
    public boolean delete(final Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() == 1;
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
