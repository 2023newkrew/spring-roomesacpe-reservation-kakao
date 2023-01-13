package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dao.preparedstatementcreator.AddReservationPreparedStatementCreator;
import roomescape.dao.preparedstatementcreator.DeleteReservationPreparedStatementCreator;
import roomescape.dao.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

@Repository
public class SpringReservationDAO extends ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpringReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private <T> void validateResult(List<T> result) {
        if (result.size() != 1) {
            throw new BadRequestException();
        }
    }

    @Override
    protected boolean existReservation(LocalDate date, LocalTime time) {
        List<Boolean> result = jdbcTemplate.query(
                new ExistReservationPreparedStatementCreator(date, time), getExistRowMapper());
        validateResult(result);
        return result.get(0);
    }

    @Override
    public Long addReservation(Reservation reservation) {
        validateReservation(reservation);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new AddReservationPreparedStatementCreator(reservation), keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public Reservation findReservation(Long id) {
        List<Reservation> result = jdbcTemplate.query(
                new FindReservationPreparedStatementCreator(id), getReservationRowMapper());
        validateResult(result);
        return result.get(0);
    }

    @Override
    public void deleteReservation(Long id) {
        jdbcTemplate.update(
                new DeleteReservationPreparedStatementCreator(id));
    }
}
