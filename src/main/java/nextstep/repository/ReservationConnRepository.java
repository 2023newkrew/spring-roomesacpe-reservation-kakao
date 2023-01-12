package nextstep.repository;

import nextstep.dto.Reservation;
import nextstep.dto.ReservationInput;
import nextstep.dto.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationConnRepository implements ReservationRepository {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet resultSet;

    private void getConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
    }

    private void closeOperation(){
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
        try {
            getConnection();

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
            closeOperation();
        }
    }

    @Override
    public Reservation find(long id) {
        try {
            getConnection();

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
            closeOperation();
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        try {
            getConnection();

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
            closeOperation();
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            getConnection();

            String sql = "DELETE FROM reservation WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            closeOperation();
        }
    }

    @Override
    public boolean duplicate(ReservationInput reservationInput) {
        try {
            getConnection();

            String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(reservationInput.getDate()));
            pstmt.setTime(2, Time.valueOf(reservationInput.getTime()));

            resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e){
            throw new RuntimeException();
        } finally {
            closeOperation();
        }
        return false;
    }
}
