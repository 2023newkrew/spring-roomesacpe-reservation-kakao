package nextstep.main.java.nextstep.console;

import nextstep.main.java.nextstep.domain.Reservation;
import nextstep.main.java.nextstep.domain.Theme;
import nextstep.main.java.nextstep.repository.ReservationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ReservationDAO implements ReservationRepository {

    @Override
    public Reservation save(Reservation reservation) {
        try (Connection connection = connect()) {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setDate(1, Date.valueOf(reservation.getDate()));
            preparedStatement.setTime(2, Time.valueOf(reservation.getTime()));
            preparedStatement.setString(3, reservation.getName());
            preparedStatement.setString(4, reservation.getTheme().getName());
            preparedStatement.setString(5, reservation.getTheme().getDesc());
            preparedStatement.setInt(6, reservation.getTheme().getPrice());

            preparedStatement.executeUpdate();
            return new Reservation(getGeneratedKey(preparedStatement), reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getGeneratedKey(PreparedStatement ps) throws SQLException {
        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getLong(1);
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.first();
            return Optional.of(
                    new Reservation(rs.getLong("id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime(),
                            rs.getString("name"),
                            new Theme(
                                    rs.getString("theme_name"),
                                    rs.getString("theme_desc"),
                                    rs.getInt("theme_price")
                            ))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOne(Long id) {
        try (Connection connection = connect()) {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        try (Connection connection = connect()) {
            String sql = "SELECT * FROM reservation WHERE date = ? AND time = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));

            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection connect() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "")) {
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
