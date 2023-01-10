package roomescape.repository;


import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ReservationConsoleRepository implements ReservationRepository {

    @Override
    public void insertReservation(Reservation reservation) {
        try(
            Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb;AUTO_SERVER=true", "sa", "");
            PreparedStatement ps = createInsertReservationPreparedStatement(con, reservation)
        ){
            ps.executeUpdate();
        } catch (SQLException e){
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private PreparedStatement createInsertReservationPreparedStatement(
            Connection con,
            Reservation reservation
    ) throws SQLException {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setString(4, reservation.getTheme().getName());
        ps.setString(5, reservation.getTheme().getDesc());
        ps.setInt(6, reservation.getTheme().getPrice());

        return ps;
    }

    @Override
    public Optional<Reservation> getReservation(Long id) {
        try(
            Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb;AUTO_SERVER=true", "sa", "");
            PreparedStatement ps = createGetReservationPreparedStatment(con, id);
            ResultSet resultSet = ps.executeQuery()
        ){
            return resultSetToReservation(resultSet);
        } catch (SQLException e){
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private PreparedStatement createGetReservationPreparedStatment(Connection con, Long id) throws SQLException {
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
                    new Theme(
                            resultSet.getString("theme_name"),
                            resultSet.getString("theme_desc"),
                            resultSet.getInt("theme_price")
                    )
            ));
        }
        return Optional.empty();
    }

    @Override
    public void deleteReservation(Long id) {
        try(
            Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb;AUTO_SERVER=true", "sa", "");
            PreparedStatement ps = createDeleteReservationPreparedStatment(con, id)
        ){
            ps.executeUpdate();
        } catch (SQLException e){
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    private PreparedStatement createDeleteReservationPreparedStatment(Connection con, Long id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);

        return ps;
    }

    @Override
    public Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time) {
        try(
            Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb;AUTO_SERVER=true", "sa", "");
            PreparedStatement ps = createGetReservationByDateAndTimePreparedStatement(con, date, time);
            ResultSet resultSet = ps.executeQuery()
        ){
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
        String sql = "SELECT * FROM reservation WHERE date = (?) and time = (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(date));
        ps.setTime(2, Time.valueOf(time));

        return ps;
    }
}
