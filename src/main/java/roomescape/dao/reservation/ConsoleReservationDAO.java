package roomescape.dao.reservation;

import java.util.List;
import roomescape.dao.ConnectionDAOManager;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationThemeIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

public class ConsoleReservationDAO implements ReservationDAO {

    private final ConnectionDAOManager connectionDAOManager;

    public ConsoleReservationDAO(String url, String user, String password) {
        connectionDAOManager = new ConnectionDAOManager(url, user, password);
    }

    private <T> void validateResult(List<T> result) {
        if (result.size() == 0) {
            throw new BadRequestException();
        }
    }

    @Override
    public boolean exist(Reservation reservation) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistReservationPreparedStatementCreator(reservation), existRowMapper);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public boolean existId(Long id) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistReservationIdPreparedStatementCreator(id), existRowMapper);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public boolean existThemeId(Long id) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistReservationThemeIdPreparedStatementCreator(id), existRowMapper);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public Long create(Reservation reservation) {
        List<Long> result = connectionDAOManager.updateAndGetKey(
                new InsertReservationPreparedStatementCreator(reservation), "id", Long.class);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public Reservation find(Long id) {
        List<Reservation> result = connectionDAOManager.query(
                new FindReservationPreparedStatementCreator(id), rowMapper);
        validateResult(result);
        return result.get(0);
    }

    @Override
    public void remove(Long id) {
        connectionDAOManager.update(
                new RemoveReservationPreparedStatementCreator(id));
    }
}
