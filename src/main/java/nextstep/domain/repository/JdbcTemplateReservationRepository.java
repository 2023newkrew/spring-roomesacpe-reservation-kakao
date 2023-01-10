package nextstep.domain.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
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
        Reservation reservation = jdbcTemplate.queryForObject(Queries.Reservation.SELECT_BY_ID, new Object[] {reservationId},(rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")),
                        rs.getString("name"),
                        new Theme(
                                rs.getString("theme_name"),
                                rs.getString("theme_desc"),
                                rs.getInt("theme_price")
                        )
                )
        );
        return Optional.ofNullable(reservation);
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return jdbcTemplate.queryForObject(Queries.Reservation.SELECT_COUNT_BY_DATE_AND_TIME, new Object[] {Date.valueOf(date), Time.valueOf(time)}, Integer.class) > 0;
    }

    @Override
    public boolean deleteById(Long reservationId) {
        return jdbcTemplate.update(Queries.Reservation.DELETE_BY_ID, new Object[] {reservationId}) == 1;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(Queries.Reservation.DELETE_ALL);
    }
}
