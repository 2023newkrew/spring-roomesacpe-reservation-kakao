package roomescape.reservation.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private static final int INDEX_DATE = 1;
    private static final int INDEX_TIME = 2;
    private static final int INDEX_NAME = 3;
    private static final int INDEX_THEME_ID = 4;
    private static final String INSERT_QUERY = "INSERT INTO RESERVATION (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
    private static final String SELECT_BY_NAME_TIME_QUERY = "SELECT * FROM RESERVATION WHERE date = ? AND time = ?;";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM RESERVATION WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM RESERVATION WHERE id = ?;";
    final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationRowMapper =
            (resultSet, rowNum) -> ReservationMapper.mapToReservation(resultSet);

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ReservationRequest reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setDate(INDEX_DATE, Date.valueOf(reservation.getDate()));
            ps.setTime(INDEX_TIME, Time.valueOf(reservation.getTime()));
            ps.setString(INDEX_NAME, reservation.getName());
            ps.setLong(INDEX_THEME_ID, reservation.getThemeId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Reservation> findDuplicatedDateAndTime(LocalDate date, LocalTime time) {
        try {
            Reservation duplicatedReservation = jdbcTemplate.queryForObject(SELECT_BY_NAME_TIME_QUERY, reservationRowMapper, date, time);
            return Optional.ofNullable(duplicatedReservation);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Reservation> findById(Long reservationId) {
        try {
            Reservation reservation = jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, reservationRowMapper, reservationId);
            return Optional.ofNullable(reservation);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Long reservationId) {
        jdbcTemplate.update(DELETE_QUERY, reservationId);
    }
}
