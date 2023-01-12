package kakao.repository.reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import kakao.domain.Reservation;
import kakao.domain.Theme;

public class ReservationJdbcRepository implements ReservationRepository{
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true";

    public Reservation save(Reservation reservation) {
        String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        // 드라이버 연결
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"id"});
        ) {
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    return null;
                }
                reservation.setId(rs.getLong(1));
                return reservation;
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

    public Reservation findById(Long id) {
        String SELECT_SQL = "select * from reservation join theme on reservation.theme_id = theme.id where reservation.id=?";
        // 드라이버 연결
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(SELECT_SQL);
        ) {
            ps.setLong(1, id);
            try (
                    ResultSet rs = ps.executeQuery();
            ) {
                if (!rs.next()) return null;
                Theme theme = Theme.builder()
                        .id(rs.getLong(6))
                        .name(rs.getString(7))
                        .desc(rs.getString(8))
                        .price(rs.getInt(9))
                        .build();
                return Reservation.builder()
                        .id(rs.getLong(1))
                        .date(rs.getDate(2).toLocalDate())
                        .time(rs.getTime(3).toLocalTime())
                        .name(rs.getString(4))
                        .theme(theme)
                        .build();
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

    @Override
    public List<Reservation> findByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        String SELECT_SQL = "select * from reservation join theme on reservation.theme_id = theme.id where theme.id = ? and date=? and time=?";
        List<Reservation> reservations = new ArrayList<>();
        // 드라이버 연결
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(SELECT_SQL);
        ) {
            ps.setLong(1, themeId);
            ps.setDate(2, Date.valueOf(date));
            ps.setTime(3, Time.valueOf(time));
            try (
                    ResultSet rs = ps.executeQuery();
            ) {
                while (rs.next()) {
                    Theme theme = Theme.builder()
                            .id(rs.getLong(5))
                            .name(rs.getString(6))
                            .desc(rs.getString(7))
                            .price(rs.getInt(8))
                            .build();
                    reservations.add(Reservation.builder()
                            .id(rs.getLong(1))
                            .date(rs.getDate(2).toLocalDate())
                            .time(rs.getTime(3).toLocalTime())
                            .name(rs.getString(4))
                            .theme(theme)
                            .build());
                }
                return reservations;
            } catch (SQLException e) {
                System.err.println("반환 오류:" + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
        }
        return reservations;
    }

    public int delete(Long id) {
        String DELETE_SQL = "delete from reservation where id=?";
        try (
                Connection con = DriverManager.getConnection(DB_URL, "sa", "");
                PreparedStatement ps = con.prepareStatement(DELETE_SQL);
        ) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
