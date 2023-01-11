package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.domain.Reservation;
import nextstep.domain.theme.domain.Theme;
import nextstep.exceptions.exception.ReservationNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    public ReservationJdbcRepository() {
        Connection con = null;
        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            con.prepareStatement("DROP TABLE reservation IF EXISTS").execute();
            con.prepareStatement("CREATE TABLE RESERVATION" +
                    "(" +
                    "    id          bigint not null auto_increment," +
                    "    date        date," +
                    "    time        time," +
                    "    name        varchar(20)," +
                    "    theme_name  varchar(20)," +
                    "    theme_desc  varchar(255)," +
                    "    theme_price int," +
                    "    primary key (id)" +
                    ");").executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (con != null)
                con.close();
        } catch (
                SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }
    }

    public Long save(Reservation reservation) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        Long id = null;
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

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }

        return id;
    }

    public int countByDateAndTime(LocalDate date, LocalTime time) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        int count = 0;
        try {
            String sql = "SELECT count(*) FROM reservation WHERE date = ? and time = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }

        return count;
    }

    public Reservation findById(Long id) {
        Connection con = null;
        ResultSet rs;
        Reservation reservation;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            String sql = "SELECT * FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            reservation = getResultFromResultSet(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }

        return reservation;
    }

    private static Reservation getResultFromResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            throw new ReservationNotFoundException();
        }

        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(rs.getString("theme_name"),
                        rs.getString("theme_desc"),
                        rs.getInt("theme_price"))
        );

        return reservation;
    }

    public void delete(Long id) {
        Connection con = null;

        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
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
}
