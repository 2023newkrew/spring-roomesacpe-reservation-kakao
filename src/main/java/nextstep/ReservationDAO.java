package nextstep;

import domain.Reservation;
import domain.Theme;
import kakao.dto.request.CreateReservationRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";

    public long addReservation(CreateReservationRequest reservation) {
        String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        // 드라이버 연결
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"id"})
        ) {
            System.out.println("정상적으로 연결되었습니다.");
            ps.setDate(1, Date.valueOf(reservation.date));
            ps.setTime(2, Time.valueOf(reservation.time));
            ps.setString(3, reservation.name);
            ps.setLong(4, reservation.themeId);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    return -1;
                }
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            System.err.println("연결 혹은 반환 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public Reservation findById(Long id) {
        String SELECT_SQL = "select reservation.id, reservation.date, reservation.time, reservation.name, theme.id as theme_id, theme.name as theme_name, theme.desc as theme_desc, theme.price as theme_price" +
                " from reservation join theme on reservation.theme_id=theme.id where reservation.id=? limit 1";
        // 드라이버 연결
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(SELECT_SQL)
        ) {
            System.out.println("정상적으로 연결되었습니다.");
            ps.setLong(1, id);
            try (
                    ResultSet rs = ps.executeQuery()
            ) {
                if (!rs.next()) return null;

                return Reservation.builder()
                        .id(rs.getLong(1))
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .name(rs.getString("name"))
                        .theme(new Theme(
                                rs.getLong("theme_id"),
                                rs.getString("theme_name"),
                                rs.getString("theme_desc"),
                                rs.getInt("theme_price")))
                        .build();
            }
        } catch (SQLException e) {
            System.err.println("연결 혹은 반환 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Reservation> findByThemeId(Long themeId) {
        String SELECT_SQL = "select reservation.id, reservation.date, reservation.time, reservation.name, theme.id as theme_id, theme.name as theme_name, theme.desc as theme_desc, theme.price as theme_price" +
                " from reservation join theme on reservation.theme_id=theme.id where theme.id=?";

        List<Reservation> result = new ArrayList<>();
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(SELECT_SQL)
        ) {
            System.out.println("정상적으로 연결되었습니다.");
            ps.setLong(1, themeId);
            try (
                    ResultSet rs = ps.executeQuery()
            ) {
                while (rs.next()) {
                    result.add(Reservation.builder()
                            .id(rs.getLong(1))
                            .date(rs.getDate("date").toLocalDate())
                            .time(rs.getTime("time").toLocalTime())
                            .name(rs.getString("name"))
                            .theme(new Theme(
                                    rs.getLong("theme_id"),
                                    rs.getString("theme_name"),
                                    rs.getString("theme_desc"),
                                    rs.getInt("theme_price")))
                            .build());
                }
                return result;
            }
        } catch (SQLException e) {
            System.err.println("연결 혹은 반환 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id=?";

        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            System.out.println("정상적으로 연결되었습니다.");

            ps.setLong(1, id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
