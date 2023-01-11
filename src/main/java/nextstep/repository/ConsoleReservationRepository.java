package nextstep.repository;

import nextstep.Reservation;
import nextstep.Theme;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsoleReservationRepository implements ReservationRepository {

    private Connection con = null;

    public ConsoleReservationRepository() {
        connect();
    }

    private void connect() {
        // 드라이버 연결
        try {
            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Reservation findById(Long id) {
        try {
            String sql = "select * from reservation where id = ?";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new Reservation(
                    resultSet.getLong("ID"),
                    resultSet.getDate("DATE").toLocalDate(),
                    resultSet.getTime("TIME").toLocalTime(),
                    resultSet.getString("NAME"),
                    new Theme(
                            resultSet.getString("theme_name"),
                            resultSet.getString("theme_desc"),
                            resultSet.getInt("theme_price")
                    )
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            String sql = "delete from reservation where id = ?";
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        try {
            String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
//            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setString(3, name);
            ps.setString(4, theme.getName());
            ps.setString(5, theme.getDesc());
            ps.setInt(6, theme.getPrice());
            ps.executeUpdate();

            return ps.getGeneratedKeys().getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
