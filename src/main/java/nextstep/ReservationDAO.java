package nextstep;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDAO {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    public void makeConnection(){
        con = null;
        pstmt = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void disconnection(){
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    public void addReservation(Reservation reservation) {
        // 드라이버 연결
        try {
            String sql = "INSERT INTO RESERVATION (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
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
    }

    public Reservation lookUpReservation(Long sid){
        try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM RESERVATION WHERE id=?");

            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, String.valueOf(sid));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                LocalTime time = LocalTime.parse(rs.getString("time"));
                String name = rs.getString("name");
                String themeName = rs.getString("Theme_name");
                String themeDesc = rs.getString("Theme_desc");
                Integer themePrice = rs.getInt("Theme_price");
                Theme theme = new Theme(themeName, themeDesc, themePrice);
                return new Reservation(id, date, time, name, theme);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return new Reservation(null,null,null,null,null);
    }
}