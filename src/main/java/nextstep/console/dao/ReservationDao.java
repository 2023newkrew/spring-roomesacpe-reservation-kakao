package nextstep.console.dao;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.repository.ReservationRepository;

import java.sql.*;

public class ReservationDao implements ReservationRepository {
    private Connection con;

    public ReservationDao() {
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            con = null;
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"})) {
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme()
                    .getName());
            ps.setString(5, reservation.getTheme()
                    .getDesc());
            ps.setInt(6, reservation.getTheme()
                    .getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1L;
    }

    public Reservation findById(Long id) {
        try {
            String sql = "SELECT * FROM reservation WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Theme theme = Theme.builder()
                        .name(rs.getString("theme_name"))
                        .desc(rs.getString("theme_desc"))
                        .price(rs.getInt("theme_price"))
                        .build();
                return Reservation.builder()
                        .id(rs.getLong("id"))
                        .date(rs.getDate("date")
                                .toLocalDate())
                        .time(rs.getTime("time")
                                .toLocalTime())
                        .name(rs.getString("name"))
                        .theme(theme)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM reservation WHERE ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }
}
