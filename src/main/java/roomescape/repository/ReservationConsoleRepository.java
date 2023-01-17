package roomescape.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class ReservationConsoleRepository implements CrudRepository<Reservation, Long> {

    @Value("${spring.datasource.url}")
    private String DB_URL; //= "jdbc:h2:mem:testdb;AUTO_SERVER=true";
    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Override
    public Long save(Reservation reservation) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createSaveReservationPreparedStatement(con, reservation)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public PreparedStatement createSaveReservationPreparedStatement(
            Connection con,
            Reservation reservation
    ) throws SQLException {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        
        PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setLong(4, reservation.getTheme_id());

        return ps;
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createFindOneReservationPreparedStatement(con, id);
                ResultSet resultSet = ps.executeQuery()
        ) {
            return resultSetToReservation(resultSet);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private PreparedStatement createFindOneReservationPreparedStatement(Connection con, Long id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE id = (?);";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }

    private Optional<Reservation> resultSetToReservation(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getString("name"),
                    resultSet.getLong("theme_id")
            ));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createDeleteReservationPreparedStatement(con, id)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private PreparedStatement createDeleteReservationPreparedStatement(Connection con, Long id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }

    public Optional<Reservation> findReservationByDateAndTime(LocalDate date, LocalTime time) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createFindReservationByDateAndTimePreparedStatement(con, date, time);
                ResultSet resultSet = ps.executeQuery()
        ) {
            return resultSetToReservation(resultSet);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
        }
        return Optional.empty();
    }

    private PreparedStatement createFindReservationByDateAndTimePreparedStatement(
            Connection con,
            LocalDate date,
            LocalTime time
    ) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE date = (?) and time = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));

        return ps;
    }
}
