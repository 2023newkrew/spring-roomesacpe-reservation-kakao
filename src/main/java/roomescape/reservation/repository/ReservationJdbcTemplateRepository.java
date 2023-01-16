package roomescape.reservation.repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ThemeReservation;
import roomescape.reservation.mapper.ReservationRowMapper;
import roomescape.reservation.mapper.ThemeReservationRowMapper;

@Repository
public class ReservationJdbcTemplateRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertActor;

    public ReservationJdbcTemplateRepository(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final Reservation reservation) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservation);

        return this.insertActor.executeAndReturnKey(params).longValue();
    }

    @Override
    public Optional<ThemeReservation> findById(final Long reservationId) {
        String selectSql = "SELECT * FROM reservation join theme on (reservation.theme_id = theme.id) WHERE reservation.id = (?) LIMIT 1 ";

        List<ThemeReservation> themeReservations = jdbcTemplate.query(selectSql, new ThemeReservationRowMapper(),
                reservationId);

        if (themeReservations.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(themeReservations.get(0));
    }

    @Override
    public boolean deleteById(final Long reservationId) {
        String deleteSql = "DELETE FROM reservation WHERE id = (?)";
        int rowNum = this.jdbcTemplate.update(deleteSql, reservationId);

        return rowNum > 0;
    }

    public Optional<Reservation> findByDateTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        String selectSql = "SELECT * FROM reservation WHERE date = (?) AND time = (?) AND theme_id = (?) LIMIT 1";

        List<Reservation> reservations = jdbcTemplate.query(selectSql, new ReservationRowMapper(), Date.valueOf(date),
                Time.valueOf(time), themeId);

        if (reservations.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(reservations.get(0));
    }

    @Override
    public Optional<Reservation> findByThemeId(final Long themeId) {
        String selectSql = "SELECT * FROM reservation WHERE theme_id = (?) LIMIT 1";

        List<Reservation> reservations = jdbcTemplate.query(selectSql, new ReservationRowMapper(), themeId);

        if (reservations.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(reservations.get(0));
    }
}