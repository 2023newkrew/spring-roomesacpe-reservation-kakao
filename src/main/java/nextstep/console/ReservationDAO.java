package nextstep.console;

import nextstep.Reservation;
import nextstep.Theme;

import java.sql.*;

public class ReservationDAO {
    public void addReservation(Reservation reservation) {
        Connection con = null;

        con = getConnection(con);

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
        Connection con = null;
        Reservation reservation = null;

        con = getConnection(con);

        try {
            String sql = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                String name = rs.getString("name");
                String themeName = rs.getString("theme_name");
                String themeDesc = rs.getString("theme_desc");
                Integer themePrice = rs.getInt("theme_price");

                reservation = new Reservation(
                        id,
                        date.toLocalDate(),
                        time.toLocalTime(),
                        name,
                        new Theme(themeName, themeDesc, themePrice));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return reservation;
    }

    public boolean deleteReservation(Long id) {
        Connection con = null;
        boolean deleted = false;

        con = getConnection(con);

        try {
            String sql = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                deleted = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return deleted;
    }


    private static Connection getConnection(Connection con) {
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    private static void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }
}
