package roomescape.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationDaoWeb implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationDaoWeb(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation addReservation(Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        reservation.setId(insertActor.executeAndReturnKey(parameters).longValue());
        return reservation;
    }

    public List<Reservation> findReservationById(Long id) {
        return jdbcTemplate.query(selectById, new ReservationMapper(), id);
    }

    public List<Reservation> findReservationByDateAndTime(String date, String time) {
        return jdbcTemplate.query(selectByDateAndTime, new ReservationMapper(), date, time);
    }

    public void removeReservation(Long id) {
        jdbcTemplate.update(deleteById, id);
    }

}
