package roomescape.console.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import roomescape.dto.Reservation;

public class ReservationDAO {

    private final String url;
    private final String user;
    private final String password;

    public ReservationDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void addReservation(Reservation reservation) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

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

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }
}
