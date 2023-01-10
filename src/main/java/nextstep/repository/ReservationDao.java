package nextstep.repository;

import nextstep.Reservation;
import nextstep.Theme;
import nextstep.exceptions.exception.DuplicatedDateAndTimeException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReservationDao {
    private List<Reservation> reservations = new ArrayList<>();

    public void save(Reservation reservation) {
        reservation.setId((long) (reservations.size() + 1));
        reservations.add(reservation);
    }

    public Reservation findById(Long id) {
        return reservations.get((int) (id - 1));
    }

    public void clear() {
        reservations = new ArrayList<>();
    }

    public void delete(Long id) {
        reservations.remove(id.intValue());
    }

    public Reservation findByDateAndTime(LocalDate date, LocalTime time) {
        List<Reservation> result = reservations.stream().filter(reservation -> {
            return reservation.getDate().equals(date) && reservation.getTime().equals(time);
        }).collect(Collectors.toList());
        if (result.size() != 0) {
            return result.get(0);
        }
        return null;
    }

    public Long addReservationForConsole(Reservation reservation) {
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
            String sql = "SELECT * FROM reservation WHERE date = ? and time = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new DuplicatedDateAndTimeException();
            }

            sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DuplicatedDateAndTimeException e) {
            System.out.println("해당 날짜와 시간은 이미 예약되었습니다.");
        }

        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
        }

        return id;
    }

    public Reservation findReservationForConsole(Long id) {
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
        Reservation reservation;
        if (!rs.next()) {
            System.out.println("해당 예약은 존재하지 않습니다.");
            return null;
        }
        reservation = new Reservation(
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

    public void deleteReservationForConsole(Long id) {
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
