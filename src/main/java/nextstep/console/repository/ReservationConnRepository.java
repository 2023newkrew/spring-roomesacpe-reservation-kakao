package nextstep.console.repository;

import nextstep.domain.Reservation;
import nextstep.domain.repository.ReservationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static nextstep.console.util.DBConnectionUtil.*;

public class ReservationConnRepository implements ReservationRepository {

    @Override
    public Reservation create(Reservation reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
            pstmt = conn.prepareStatement(sql, new String[]{"id"});
            pstmt.setDate(1, Date.valueOf(reservation.getDate()));
            pstmt.setTime(2, Time.valueOf(reservation.getTime()));
            pstmt.setString(3, reservation.getName());
            pstmt.setLong(4, reservation.getThemeId());
            pstmt.executeUpdate();

            resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            long id = resultSet.getLong("id");
            reservation.setId(id);
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
    }

    @Override
    public Reservation find(long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM reservation WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getString("name"),
                    resultSet.getLong("theme_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "SELECT * FROM reservation";
            pstmt = conn.prepareStatement(sql);

            resultSet = pstmt.executeQuery();
            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getString("name"),
                    resultSet.getLong("theme_id")
                ));
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
    }

    @Override
    public boolean delete(long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "DELETE FROM reservation WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
    }

    @Override
    public boolean duplicate(LocalDate date, LocalTime time) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setTime(2, Time.valueOf(time));

            resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e){
            throw new RuntimeException();
        } finally {
            closeOperation(conn, pstmt, resultSet);
        }
        return false;
    }
}
