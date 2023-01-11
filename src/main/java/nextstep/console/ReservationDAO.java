package nextstep.console;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private static final String DATASOURCE_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";
    private static final String DATASOURCE_USER = "sa";
    private static final String DATASOURCE_PASSWORD = "";

    public void addReservation(Reservation reservation) {
        Connection con = getConnection();

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
    }

    public Reservation findReservation(Long id) {
        Connection con = getConnection();
        Reservation reservation = null;

        try {
            String sql = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = getReservationFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return reservation;
    }

    public List<Reservation> findReservationByDateAndTime(LocalDate localDate, LocalTime localTime) {
        Connection con = getConnection();
        List<Reservation> reservations = new ArrayList<>();

        try {
            String sql = "SELECT * FROM reservation WHERE date = ? AND time = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(localDate));
            ps.setTime(2, Time.valueOf(localTime));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reservations.add(getReservationFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return reservations;
    }

    public boolean deleteReservation(Long id) {
        Connection con = getConnection();
        boolean deleted;

        try {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            int rowCount = ps.executeUpdate();
            deleted = rowCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return deleted;
    }

    private static Reservation getReservationFromResultSet(ResultSet resultSet) throws SQLException {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                new Theme(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                )
        );
    }

    private static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USER, DATASOURCE_PASSWORD);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }
}
