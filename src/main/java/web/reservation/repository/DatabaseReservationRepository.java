package web.reservation.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import web.entity.Reservation;
import web.reservation.exception.ReservationException;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import static web.reservation.exception.ErrorCode.RESERVATION_DUPLICATE;

@Repository
public class DatabaseReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long save(Reservation reservation) {
        if (isDuplicateReservation(reservation)) {
            throw new ReservationException(RESERVATION_DUPLICATE);
        }
        String sql = "INSERT INTO RESERVATION (date, time, name) VALUES (?, ?, ?);";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                ps.setDate(1, Date.valueOf(reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                ps.setTime(2, Time.valueOf(reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                ps.setString(3, reservation.getName());
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
                    resultSet.getDate("DATE").toLocalDate(),
                    resultSet.getTime("TIME").toLocalTime(),
                    resultSet.getString("NAME")), reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
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
                    resultSet.getDate("DATE").toLocalDate(),
                    resultSet.getTime("TIME").toLocalTime(),
                    resultSet.getString("NAME")), reservationId);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(reservation);
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
