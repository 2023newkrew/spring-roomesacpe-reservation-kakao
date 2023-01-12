package nextstep.repository;

import nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationJdbcDao implements ReservationDao {
    private Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
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

    public Long save(Reservation reservation) {
        // 드라이버 연결
        Connection con = getConnection();

        long id;
        try {
            PreparedStatement ps = getPreparedStatementCreatorForSave(reservation).createPreparedStatement(con);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return id;
    }

    public int countByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long id) {
        // 드라이버 연결
        Connection con = getConnection();

        int count = 0;
        try {
            String sql = "SELECT count(*) FROM reservation WHERE date = ? and time = ? and theme_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setLong(3, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return count;
    }

    public Optional<Reservation> findById(Long id) {
        Optional<Reservation> reservation;
        // 드라이버 연결
        Connection con = getConnection();

        try {
            String sql = "SELECT *, theme.name AS theme_name, theme.desc AS theme_desc, theme.price AS theme_price " +
                    "FROM reservation " +
                    "LEFT JOIN theme ON reservation.theme_id = theme.id " +
                    "WHERE reservation.id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            reservation = getReservationsFromResultSet(rs).stream().findAny();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
        return reservation;
    }

    private List<Reservation> getReservationsFromResultSet(ResultSet rs) throws SQLException {
        int NO_MEANING = 0;
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()) {
            reservations.add(getRowMapper().mapRow(rs, NO_MEANING));
        }
        return reservations;
    }

    public void delete(Long id) {
        // 드라이버 연결
        Connection con = getConnection();

        try {
            String sql = "DELETE FROM reservation WHERE id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeConnection(con);
    }
}
