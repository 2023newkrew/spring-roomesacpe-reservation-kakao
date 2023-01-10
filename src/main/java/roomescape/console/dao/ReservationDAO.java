package roomescape.console.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

public class ReservationDAO {

    private static final String ADD_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String FIND_SQL = "SELECT * FROM reservation WHERE id=?;";
    private static final String DELETE_SQL = "DELETE FROM reservation WHERE id=?;";

    private final String url;
    private final String user;
    private final String password;

    public ReservationDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private void executeAddConnection(Connection con, Reservation reservation) {
        try {
            PreparedStatement ps = con.prepareStatement(ADD_SQL);
            setReservationStatement(ps, reservation);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Reservation executeFindConnection(Connection con, Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(FIND_SQL);
            setIdStatement(ps, id);
            ResultSet resultSet = ps.executeQuery();
            return parseFindResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeDeleteConnection(Connection con, Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(DELETE_SQL);
            setIdStatement(ps, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setReservationStatement(PreparedStatement ps, Reservation reservation)
            throws SQLException {
        ps.setDate(1, Date.valueOf(reservation.getDate()));
        ps.setTime(2, Time.valueOf(reservation.getTime()));
        ps.setString(3, reservation.getName());
        ps.setString(4, reservation.getTheme().getName());
        ps.setString(5, reservation.getTheme().getDesc());
        ps.setInt(6, reservation.getTheme().getPrice());
    }

    private void setIdStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    private Reservation parseFindResultSet(ResultSet resultSet) throws SQLException {
        validateResultSet(resultSet);
        Long id = resultSet.getLong(1);
        Date date = resultSet.getDate(2);
        Time time = resultSet.getTime(3);
        String name = resultSet.getString(4);
        String theme_name = resultSet.getString(5);
        String theme_desc = resultSet.getString(6);
        int theme_price = resultSet.getInt(7);
        return new Reservation(id, date.toLocalDate(), time.toLocalTime(), name,
                new Theme(theme_name, theme_desc, theme_price));
    }

    private void validateResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new SQLException();
        }
    }

    private Connection openConnection() {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        return con;
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
        }
    }

    public void addReservation(Reservation reservation) {
        Connection con = openConnection();
        executeAddConnection(con, reservation);
        closeConnection(con);
    }

    public Reservation findReservation(Long id) {
        Connection con = openConnection();
        Reservation reservation = executeFindConnection(con, id);
        closeConnection(con);
        return reservation;
    }

    public void deleteReservation(Long id) {
        Connection con = openConnection();
        executeDeleteConnection(con, id);
        closeConnection(con);
    }
}
