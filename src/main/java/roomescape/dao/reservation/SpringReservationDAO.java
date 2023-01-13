package roomescape.dao.reservation;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

@Repository
public class SpringReservationDAO extends ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpringReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private <T> void validateResult(List<T> result) {
        if (result.size() == 0) {
            throw new BadRequestException();
        }
    }

    @Override
    public boolean exist(Reservation reservation) {
        List<Boolean> result = jdbcTemplate.query(
                new ExistReservationPreparedStatementCreator(reservation), getExistRowMapper());
        validateResult(result);
        return result.get(0);
    }

    @Override
    public boolean existId(Long id) {
        List<Boolean> result = jdbcTemplate.query(
                new ExistReservationIdPreparedStatementCreator(id), getExistRowMapper());
        validateResult(result);
        return result.get(0);
    }

    @Override
    public Long create(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new InsertReservationPreparedStatementCreator(reservation), keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public Reservation find(Long id) {
        List<Reservation> result = jdbcTemplate.query(
                new FindReservationPreparedStatementCreator(id), getRowMapper());
        validateResult(result);
        return result.get(0);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(
                new RemoveReservationPreparedStatementCreator(id));
    }
}
