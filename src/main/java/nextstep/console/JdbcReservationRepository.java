package nextstep.console;

import nextstep.model.Reservation;
import nextstep.repository.ReservationSQL;
import nextstep.util.JdbcRemoveDuplicateUtils;
import nextstep.repository.ReservationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JdbcReservationRepository implements ReservationRepository {

    public void checkRecordExists(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("레코드가 존재하지 않습니다.");
        }
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = ReservationSQL.INSERT.toString();

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"})) {
            JdbcRemoveDuplicateUtils.setReservationToStatement(ps, reservation);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            checkRecordExists(rs);

            Long id = rs.getLong("id");
            return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getThemeId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = ReservationSQL.SELECT_BY_ID.toString();

        try (Connection con = createConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            checkRecordExists(rs);

            Reservation reservation = JdbcRemoveDuplicateUtils.getReservationFromResultSet(rs, id);
            return Optional.of(reservation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        String sql = ReservationSQL.SELECT_BY_THEME_ID.toString();

        List<Reservation> reservations = new ArrayList<>();
        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, themeId);
            ResultSet rs = ps.executeQuery();
            checkRecordExists(rs);

            Long id = rs.getLong("id");
            reservations.add(JdbcRemoveDuplicateUtils.getReservationFromResultSet(rs, id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = ReservationSQL.COUNT_BY_DATE_AND_TIME.toString();

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ResultSet rs = ps.executeQuery();

            return rs.next() && rs.getInt("count") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = ReservationSQL.DELETE_BY_ID.toString();

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection createConnection() {
        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=true", "sa", "");
            System.out.println("정상적으로 연결되었습니다.");
            return con;
        } catch (SQLException e) {
            System.err.println("연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
