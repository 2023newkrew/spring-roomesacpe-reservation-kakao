package roomescape.reservation.repository.web;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.common.AbstractReservationH2Repository;
import roomescape.reservation.repository.common.ReservationMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationRepositoryWeb extends AbstractReservationH2Repository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationRepositoryWeb(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation add(Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        reservation.setId(insertActor.executeAndReturnKey(parameters).longValue());
        return reservation;
    }

    @Override
    public Reservation get(Long id) {
        List<Reservation> reservations = jdbcTemplate.query(selectByIdQuery, new ReservationMapper(), id);
        if (reservations.isEmpty()) {
            return null;
        }
        return reservations.get(0);
    }

    @Override
    public Reservation get(String date, String time) {
        List<Reservation> reservations = jdbcTemplate.query(selectByDateAndTimeQuery, new ReservationMapper(), date, time);
        if (reservations.isEmpty()) {
            return null;
        }
        return reservations.get(0);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(deleteByIdQuery, id);
    }

}
