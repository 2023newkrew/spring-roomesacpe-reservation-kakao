package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

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
    final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationRowMapper =
            (resultSet, rowNum) -> ReservationMapper.mapToReservation(resultSet);

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (date, time, name, theme_id) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(INDEX_DATE, Date.valueOf(reservation.getDate()));
            ps.setTime(INDEX_TIME, Time.valueOf(reservation.getTime()));
            ps.setString(INDEX_NAME, reservation.getName());
            ps.setLong(INDEX_THEME_ID, reservation.getThemeId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<Reservation> findDuplicatedDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM RESERVATION WHERE date = ? AND time = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, reservationRowMapper, date, time));
    }

    public Reservation findById(String reservationId) {
        String sql = "SELECT * FROM RESERVATION WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, Long.parseLong(reservationId));
    }

    public void deleteById(String reservationId) {
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(sql, reservationId);
    }
}
