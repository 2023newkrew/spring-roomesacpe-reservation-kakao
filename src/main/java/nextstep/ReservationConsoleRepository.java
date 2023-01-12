package nextstep;

import roomescape.model.Reservation;
import roomescape.model.Theme;
import roomescape.repository.ReservationRepository;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ReservationConsoleRepository implements ReservationRepository {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "";

    @Override
    public long save(Reservation reservation) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            return (long) ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reservation> findOneById(long reservationId) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "select * from reservation join theme on reservation.theme_id = theme.id where reservation.id = ? limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return Optional.of(new Reservation(
                    resultSet.getLong("reservation.id"),
                    resultSet.getDate("reservation.date").toLocalDate(),
                    resultSet.getTime("reservation.time").toLocalTime(),
                    resultSet.getString("reservation.name"),
                    new Theme(
                            resultSet.getLong("theme.id"),
                            resultSet.getString("theme.name"),
                            resultSet.getString("theme.desc"),
                            resultSet.getInt("theme.price")
                    )
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long reservationId) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean hasOneByDateAndTimeAndTheme(LocalDate date, LocalTime time, Long themeId) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "select * from reservation where date = ? and time = ? and theme_id = ? limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setLong(3, themeId);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean hasReservationOfTheme(long themeId) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {
            String sql = "select * from reservation where theme_id = ? limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, themeId);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
