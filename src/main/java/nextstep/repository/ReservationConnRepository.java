package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationDTO;
import nextstep.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationConnRepository implements ReservationRepository {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
    }

    private void closeOperation(Connection conn, PreparedStatement pstmt, ResultSet resultSet){
        try{
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {}
        try{
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
        } catch (SQLException e){}
        try {
            if (conn != null){
                conn.close();
                conn = null;
            }
        } catch (SQLException e){}
    }

    @Override
    public Reservation create(Reservation reservation) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();

            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            pstmt = conn.prepareStatement(sql, new String[]{"id"});
            pstmt.setDate(1, Date.valueOf(reservation.getDate()));
            pstmt.setTime(2, Time.valueOf(reservation.getTime()));
            pstmt.setString(3, reservation.getName());
            pstmt.setString(4, reservation.getTheme().getName());
            pstmt.setString(5, reservation.getTheme().getDesc());
            pstmt.setInt(6, reservation.getTheme().getPrice());
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
                Theme theme = new Theme(
                    resultSet.getString("theme_name"),
                    resultSet.getString("theme_desc"),
                    resultSet.getInt("theme_price")
                );
                return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getString("name"),
                    theme
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
                Theme theme = new Theme(
                    resultSet.getString("theme_name"),
                    resultSet.getString("theme_desc"),
                    resultSet.getInt("theme_price")
                );
                 reservations.add(new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getString("name"),
                    theme
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
