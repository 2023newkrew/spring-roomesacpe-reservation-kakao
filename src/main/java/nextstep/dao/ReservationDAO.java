package nextstep.dao;

import nextstep.Reservation;
import nextstep.Theme;

import java.sql.*;
import java.util.Optional;

public class ReservationDAO {

    private static final String CONNECTION_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";

    public void addReservation(Reservation reservation) {
        Connection con = null;

        try {
            con = DriverManager.getConnection(CONNECTION_URL, "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
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

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    public Optional<Reservation> findReservation(long reservationId) {
        Connection con = null;
        ResultSet rs = null;
        Reservation reservation = null;

        try {
            con = DriverManager.getConnection(CONNECTION_URL, "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            String sql = "SELECT * FROM RESERVATION WHERE ID=?;";
            PreparedStatement ps = con.prepareStatement(sql, new String[] {"id"});
            ps.setLong(1, reservationId);
            rs = ps.executeQuery();
            if (rs.next()) {
                reservation = new Reservation(
                        rs.getLong("ID"),
                        rs.getDate("DATE").toLocalDate(),
                        rs.getTime("TIME").toLocalTime(),
                        rs.getString("NAME"),
                        new Theme(rs.getString("THEME_NAME"),
                                rs.getString("THEME_DESC"),
                                rs.getInt("THEME_PRICE"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }

        return Optional.ofNullable(reservation);
    }

    public Long deleteReservation(long reservationId) {
        Connection con = null;

        try {
            con = DriverManager.getConnection(CONNECTION_URL, "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        Long res;
        try {
            String sql = "DELETE FROM RESERVATION WHERE id=?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            res = new Long(ps.executeUpdate());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
        return res;
    }
}
