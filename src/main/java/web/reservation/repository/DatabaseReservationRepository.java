package web.reservation.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import web.entity.Reservation;
import web.exception.ErrorCode;
import web.reservation.exception.ReservationException;
import web.theme.repository.ThemeRepository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DatabaseReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ThemeRepository themeRepository;

    public DatabaseReservationRepository(DataSource dataSource, ThemeRepository themeRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.themeRepository = themeRepository;
    }

    @Override
    public long save(Reservation reservation) {
        if (isDuplicateReservation(reservation)) {
            throw new ReservationException(ErrorCode.RESERVATION_DUPLICATE);
        }
        String sql = "INSERT INTO RESERVATION (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                ps.setDate(1, Date.valueOf(reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                ps.setTime(2, Time.valueOf(reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                ps.setString(3, reservation.getName());
                ps.setLong(4, reservation.getThemeId());
                return ps;
            }, keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        } catch (Exception E) {
            return -1;
        }
    }

    private boolean isDuplicateReservation(Reservation reservation) {
        String sql = "SELECT * FROM RESERVATION WHERE DATE = ? AND TIME = ?";
        Reservation findReservation;
        try {
            findReservation = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> Reservation.of(
                    resultSet.getLong("ID"),
                    resultSet.getDate("DATE").toLocalDate(),
                    resultSet.getTime("TIME").toLocalTime(),
                    resultSet.getString("NAME"),
                    resultSet.getLong("THEME_ID")), reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        } catch (DataAccessException e) {
            return false;
        }
        return findReservation != null;
    }

    @Override
    public Optional<Reservation> findById(long reservationId) {
        String sql = "SELECT * FROM RESERVATION WHERE ID = ?";
        Reservation reservation;
        try {
            reservation = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> Reservation.of(
                    resultSet.getLong("ID"),
                    resultSet.getDate("DATE").toLocalDate(),
                    resultSet.getTime("TIME").toLocalTime(),
                    resultSet.getString("NAME"),
                    resultSet.getLong("THEME_ID")), reservationId);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(reservation);
    }

    @Override
    public List<Reservation> findAllByThemeId(long themeId) {
        String sql = "SELECT * FROM RESERVATION WHERE THEME_ID = ?";
        List<Reservation> reservations;
        try {
            reservations = jdbcTemplate.query(sql, (resultSet, rowNum) -> Reservation.of(
                    resultSet.getLong("ID"),
                    resultSet.getDate("DATE").toLocalDate(),
                    resultSet.getTime("TIME").toLocalTime(),
                    resultSet.getString("NAME"),
                    resultSet.getLong("THEME_ID")), themeId);
        } catch (DataAccessException e) {
            return List.of();
        }
        return reservations;
    }

    @Override
    public Long delete(long reservationId) {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";
        return (long) jdbcTemplate.update(sql, reservationId);
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM RESERVATION";
        jdbcTemplate.update(sql);
    }
}
