package web.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.entity.Reservation;
import web.exception.ReservationDuplicateException;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Repository
public class ReservationDaoImpl implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public Long save(Reservation reservation) {
        if (isDuplicateReservation(reservation)) {
            throw new ReservationDuplicateException();
        }
        String sql = "INSERT INTO reservation (date, time, name) VALUES (?, ?, ?);";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setDate(1, Date.valueOf(reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                ps.setTime(2, Time.valueOf(reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                ps.setString(3, reservation.getName());
                return ps;
            }, keyHolder);
            return new Long(keyHolder.getKey().longValue());
        } catch (Exception E) {
            return null;
        }
    }

    @Transactional
    public boolean isDuplicateReservation(Reservation reservation) {
        String sql = "SELECT * FROM reservation WHERE DATE = ? AND TIME = ?";
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
    @Transactional
    public Optional<Reservation> findById(long reservationId) {
        String sql = "SELECT * FROM reservation WHERE ID = ?";
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
    @Transactional
    public Long delete(long reservationId) {
        String sql = "DELETE FROM reservation WHERE ID = ?";
        return (long) jdbcTemplate.update(sql, reservationId);
    }


    @Override
    public void clearAll() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }
}
