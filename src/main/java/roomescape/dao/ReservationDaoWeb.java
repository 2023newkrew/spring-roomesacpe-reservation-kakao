package roomescape.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

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

    public Long addReservation(Reservation reservation) { //예약을 추가하고 키를 리턴한다.
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        reservation.setId(insertActor.executeAndReturnKey(parameters).longValue());
        return reservation.getId();
    }

    public Integer checkSchedule(ReservationDto reservationDto) {
        return jdbcTemplate.queryForObject(countByDateAndTime, Integer.class, reservationDto.getDate(), reservationDto.getTime());
    }

    public List<Reservation> findReservation(Long id) {
        return jdbcTemplate.query(selectById, new ReservationMapper(), id);
    }

    public int removeReservation(Long id) {
        return jdbcTemplate.update(deleteById, id);
    }

}
