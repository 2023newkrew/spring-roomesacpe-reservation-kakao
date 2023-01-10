package nextstep.domain.repository;

import nextstep.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservation);
        Long reservationId = jdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return new Reservation(reservationId, reservation);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return Optional.empty();
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return jdbcTemplate.queryForObject(Queries.Reservation.SELECT_COUNT_BY_DATE_AND_TIME_SQL, new Object[] {Date.valueOf(date), Time.valueOf(time)}, Integer.class) > 0;
    }

    @Override
    public boolean deleteById(Long reservationId) {
        return false;
    }

    @Override
    public void deleteAll() {

    }
}
