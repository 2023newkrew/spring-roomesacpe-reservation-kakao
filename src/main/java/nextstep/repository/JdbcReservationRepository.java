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


public class JdbcReservationRepository implements ReservationRepository {

    private final DataSource dataSource;
    private final Logger logger;

    public JdbcReservationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.logger = LoggerFactory.getLogger(JdbcReservationRepository.class);
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"})) {
            ReservationConverter.set(ps, reservation);
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
        String sql = "SELECT date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Reservation reservation = ReservationConverter.get(rs, id);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) as count FROM reservation WHERE date=? AND time=?";

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
            logger.debug("정상적으로 연결되었습니다.");
            return con;
        } catch (SQLException e) {
            logger.error("연결 오류: " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
