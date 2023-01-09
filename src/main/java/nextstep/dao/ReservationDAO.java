package nextstep.dao;

import static nextstep.domain.ThemeConstants.THEME_DESC;
import static nextstep.domain.ThemeConstants.THEME_NAME;
import static nextstep.domain.ThemeConstants.THEME_PRICE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.ReservationRequestDTO;

public class ReservationDAO {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
    }

    private static Reservation getReservation(ResultSet rs) throws SQLException {
        return new Reservation(rs.getLong("id"), rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(), rs.getString("name"), new Theme(rs.getString("theme_name"),
                rs.getString("theme_desc"), rs.getInt("theme_price")));
    }

    private static Reservation getReservation(ResultSet rs, ReservationRequestDTO dto) throws SQLException {
        return new Reservation(rs.getLong(1), dto.getDate(),
                dto.getTime(), dto.getName(),
                new Theme(THEME_NAME, THEME_DESC, THEME_PRICE
                ));
    }

    public Reservation addReservation(ReservationRequestDTO requestDTO) {
        Connection con = null;
        Reservation reservation = null;
        // 드라이버 연결
        try {
            con = getConnection();
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(requestDTO.getDate()));
            ps.setTime(2, Time.valueOf(requestDTO.getTime()));
            ps.setString(3, requestDTO.getName());
            ps.setString(4, THEME_NAME);
            ps.setString(5, THEME_DESC);
            ps.setInt(6, THEME_PRICE);
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                reservation = getReservation(resultSet, requestDTO);
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
        return reservation;
    }

    public Reservation findById(Long id) {
        Connection con = null;
        Reservation reservation = null;

        // 드라이버 연결
        try {
            con = getConnection();
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        try {
            String sql = "SELECT * FROM RESERVATION WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = getReservation(rs);

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
        return reservation;
    }

    public void deleteById(Long id) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = getConnection();
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        try {
            String sql = "DELETE FROM RESERVATION WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
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