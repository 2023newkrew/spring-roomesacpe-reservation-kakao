package nextstep.main.java.nextstep.console;

import nextstep.main.java.nextstep.mvc.domain.reservation.Reservation;
import nextstep.main.java.nextstep.mvc.domain.reservation.request.ReservationCreateRequest;
import nextstep.main.java.nextstep.mvc.domain.theme.Theme;
import nextstep.main.java.nextstep.mvc.repository.reservation.ReservationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Deprecated
public class ReservationDAO implements ReservationRepository {
    private static final String SERVER_URL = "jdbc:h2:~/test;AUTO_SERVER=true";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";
//
//    @Override
//    public Reservation save(Reservation reservation) {
//        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
//
//        try (Connection connection = connect();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"})) {
//
//            preparedStatement.setDate(1, Date.valueOf(reservation.getDate()));
//            preparedStatement.setTime(2, Time.valueOf(reservation.getTime()));
//            preparedStatement.setString(3, reservation.getName());
//            preparedStatement.setString(4, reservation.getTheme().getName());
//            preparedStatement.setString(5, reservation.getTheme().getDesc());
//            preparedStatement.setInt(6, reservation.getTheme().getPrice());
//
//            preparedStatement.executeUpdate();
//            return new Reservation(getGeneratedKey(preparedStatement), reservation);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Long save(ReservationCreateRequest request) {
        return null;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            return Optional.of(
                    Reservation.builder()
                            .id(rs.getLong("id"))
                            .date(rs.getDate("date").toLocalDate())
                            .time(rs.getTime("time").toLocalTime())
                            .name(rs.getString("name"))
                            .theme(new Theme(
                                    rs.getLong("id"),
                                    rs.getString("theme_name"),
                                    rs.getString("theme_desc"),
                                    rs.getInt("theme_price")))
                            .build()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        try (Connection connection = connect();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM reservation WHERE date = ? AND time = ?";

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));

            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsById(Long id) {
        return null;
    }

    @Override
    public Boolean existsByThemeId(Long themeId) {

        return null;
    }

    private Long getGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(SERVER_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
