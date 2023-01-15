package nextstep.reservation.repository.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;

import java.sql.*;
import nextstep.reservation.exceptions.exception.CustomSqlException;


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
            PreparedStatement pstmt = getAddReservationPreparedStatement(reservation, connection);
            ResultSet rs = pstmt.getGeneratedKeys();
            return getReservationIdFromResultSet(rs);
        }
        catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static PreparedStatement getAddReservationPreparedStatement(Reservation reservation,
            Connection connection) throws SQLException {
        String sql = "INSERT INTO reservation SET date = ?, time = ?, name = ?, theme_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
        pstmt.setDate(1, Date.valueOf(reservation.getDate()));
        pstmt.setTime(2, Time.valueOf(reservation.getTime()));
        pstmt.setString(3, reservation.getName());
        pstmt.setLong(4, reservation.getTheme().getId());
        pstmt.executeUpdate();
        return pstmt;
    }

    private static long getReservationIdFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getLong("id");
        }
        return -1L;
    }

    public Optional<Reservation> findById(Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement pstmt = getFindReservationByIdPreparedStatement(id, connection);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? Optional.of(getReservationFromResultSet(rs)) : Optional.empty();
        }
        catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static PreparedStatement getFindReservationByIdPreparedStatement(
            Long id,
            Connection connection
    ) throws SQLException {
        String sql = "SELECT * FROM reservation JOIN theme ON reservation.theme_id = theme.id WHERE reservation.id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        return pstmt;
    }

    private static Reservation getReservationFromResultSet(ResultSet rs) throws SQLException {
        return Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .theme(Theme.builder()
                        .id(rs.getLong("theme.id"))
                        .name(rs.getString("theme.name"))
                        .desc(rs.getString("theme.desc"))
                        .price(rs.getInt("theme.price"))
                        .build())
                .build();
    }

    @Override
    public List<Reservation> findAll() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM reservation JOIN theme ON reservation.theme_id = theme.id";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            return getReservationsFromResultSet(rs);
        }
        catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static List<Reservation> getReservationsFromResultSet(ResultSet rs) throws SQLException{
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()) {
            reservations.add(getReservationFromResultSet(rs));
        }
        return reservations;
    }


    @Override
    public boolean delete(final Long id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement pstmt = getPreparedStatement(id, connection);
            return pstmt.executeUpdate() == 1;
        }
        catch (SQLException e) {
            throw new CustomSqlException(e.getMessage());
        }
    }

    private static PreparedStatement getPreparedStatement(Long id, Connection connection)
            throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, id);
        return pstmt;
    }
}
