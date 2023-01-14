package roomescape.dao.reservation;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dao.DAOResult;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationThemeIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
import roomescape.dto.Reservation;

@Repository
public class SpringReservationDAO implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public SpringReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean exist(Reservation reservation) {
        List<Boolean> result = jdbcTemplate.query(
                new ExistReservationPreparedStatementCreator(reservation), existRowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public boolean existId(Long id) {
        List<Boolean> result = jdbcTemplate.query(
                new ExistReservationIdPreparedStatementCreator(id), existRowMapper);
        return DAOResult.getResult(result);
    }
    @Override
    public boolean existThemeId(Long id) {
        List<Boolean> result = jdbcTemplate.query(
                new ExistReservationThemeIdPreparedStatementCreator(id), existRowMapper);
        return DAOResult.getResult(result);
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
                new FindReservationPreparedStatementCreator(id), rowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(
                new RemoveReservationPreparedStatementCreator(id));
    }
}
