package nextstep;

import java.sql.*;

public class ReservationDAO {
    public void addReservation(Reservation reservation) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
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
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    public Reservation findById(Long id) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            String sql = "select * from reservation where id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            return new Reservation(
                    rs.getLong(1),
                    rs.getDate(2).toLocalDate(),
                    rs.getTime(3).toLocalTime(),
                    rs.getString(4),
                    new Theme(rs.getString(5), rs.getString(6), rs.getInt(7))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.err.println("con 오류:" + e.getMessage());
            }
        }
    }

    public int delete(Long id) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            String sql = "delete from reservation where id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.err.println("con 오류:" + e.getMessage());
            }
        }
    }
}
