package nextstep.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.sql.DataSource;
import nextstep.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConsoleReservationRepository implements ReservationRepository {

    private static final ReservationRowMapper ROW_MAPPER = new ReservationRowMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleReservationRepository.class);

    private final DataSource dataSource;

    public ConsoleReservationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?);";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"})) {
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            Long id = resultSet.getLong("id");
            return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(),
                    reservation.getTheme());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql =
                "SELECT r.id reservation_id, r.date reservation_date, r.time reservation_time, r.name reservation_name, "
                        + "t.name theme_name, t.desc theme_desc, t.price theme_price, t.id theme_id "
                        + "FROM reservation r JOIN theme t ON r.theme_id = t.id "
                        + "WHERE r.id = ?";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Reservation reservation = ROW_MAPPER.mapRow(rs, rs.getRow());
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        String sql = "SELECT count(*) as count FROM reservation WHERE date=? AND time=? AND theme_id = ?";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setLong(3, themeId);
            ResultSet rs = ps.executeQuery();

            return rs.next() && rs.getInt("count") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM reservation";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection createConnection() {
        try {
            Connection con = dataSource.getConnection();
            LOGGER.debug("정상적으로 연결되었습니다.");
            return con;
        } catch (SQLException e) {
            LOGGER.error("연결 오류: " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
