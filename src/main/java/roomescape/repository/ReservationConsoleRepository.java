package roomescape.repository;


import roomescape.domain.Reservation;
import roomescape.repository.mapper.ReservationMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


public class ReservationConsoleRepository implements ReservationRepository {

    private final String DB_URL = "jdbc:h2:mem:testdb;AUTO_SERVER=true";
    private final String DB_USERNAME = "sa";
    private final String DB_PASSWORD = "";

    @Override
    public Long insertReservation(Reservation reservation) {
        ResultSet resultSet = null;
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createInsertReservationPreparedStatement(con, reservation)
        ) {
            ps.executeUpdate();
            resultSet = ps.getGeneratedKeys();
            return resultSetToReservationId(resultSet);
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        return -1L;
    }

    private PreparedStatement createInsertReservationPreparedStatement(
            Connection con,
            Reservation reservation
    ) throws SQLException {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setLong(4, reservation.getTheme().getId());

        return ps;
    }

    private Long resultSetToReservationId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getLong("id");
        }
        return -1L;
    }

    @Override
    public Optional<Reservation> getReservation(Long id) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createGetReservationPreparedStatement(con, id);
                ResultSet resultSet = ps.executeQuery()
        ) {
            return resultSetToReservation(resultSet);
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private PreparedStatement createGetReservationPreparedStatement(Connection con, Long id) throws SQLException {
        String sql = "SELECT * FROM reservation " +
                "JOIN theme ON reservation.theme_id = theme.id " +
                "WHERE reservation.id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }

    private Optional<Reservation> resultSetToReservation(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(ReservationMapper.mapToReservation(resultSet));
        }
        return Optional.empty();
    }

    @Override
    public void deleteReservation(Long id) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createDeleteReservationPreparedStatement(con, id)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private PreparedStatement createDeleteReservationPreparedStatement(Connection con, Long id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }

    @Override
    public Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createGetReservationByDateAndTimePreparedStatement(con, date, time);
                ResultSet resultSet = ps.executeQuery()
        ) {
            return resultSetToReservation(resultSet);
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
        }
        return Optional.empty();
    }

    private PreparedStatement createGetReservationByDateAndTimePreparedStatement(
            Connection con,
            LocalDate date,
            LocalTime time
    ) throws SQLException {
        String sql = "SELECT * FROM reservation " +
                "JOIN theme ON reservation.theme_id = theme.id " +
                "WHERE date = (?) and time = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));

        return ps;
    }

    @Override
    public void deleteAllReservations() {
        String sql = "DELETE FROM reservation";
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
        }
    }

    @Override
    public void deleteReservationByThemeId(Long themeId) {
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement ps = createDeleteReservationByThemIdPreparedStatement(con, themeId)
        ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private PreparedStatement createDeleteReservationByThemIdPreparedStatement(Connection con, Long themeId) throws SQLException {
        String sql = "DELETE FROM reservation WHERE theme_id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, themeId);

        return ps;
    }
}
