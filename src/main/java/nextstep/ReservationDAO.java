package nextstep;

import domain.Reservation;
import domain.Theme;

import java.sql.*;

public class ReservationDAO {
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";

    public long addReservation(Reservation reservation) {
        String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        // 드라이버 연결
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"id"})
        ) {
            System.out.println("정상적으로 연결되었습니다.");
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    return -1;
                }
                return rs.getLong(1);
            } catch (SQLException e) {
                System.err.println("반환 오류:" + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public Reservation findById(Long id) {
        String SELECT_SQL = "select * from reservation where id=?";
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

                return new Reservation(
                        rs.getLong(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getTime(3).toLocalTime(),
                        rs.getString(4),
                        new Theme(rs.getString(5), rs.getString(6), rs.getInt(7)));
            } catch (SQLException e) {
                System.err.println("반환 오류:" + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
