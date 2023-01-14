package roomescape.dao.reservation;

import java.util.List;
import roomescape.dao.ConnectionDAOManager;
import roomescape.dao.DAOResult;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationThemeIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
import roomescape.dto.Reservation;

public class ConsoleReservationDAO implements ReservationDAO {

    private final ConnectionDAOManager connectionDAOManager;

    public ConsoleReservationDAO(String url, String user, String password) {
        connectionDAOManager = new ConnectionDAOManager(url, user, password);
    }

    @Override
    public boolean exist(Reservation reservation) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistReservationPreparedStatementCreator(reservation), existRowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public boolean existId(Long id) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistReservationIdPreparedStatementCreator(id), existRowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public boolean existThemeId(Long id) {
        List<Boolean> result = connectionDAOManager.query(
                new ExistReservationThemeIdPreparedStatementCreator(id), existRowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public Long create(Reservation reservation) {
        List<Long> result = connectionDAOManager.updateAndGetKey(
                new InsertReservationPreparedStatementCreator(reservation), "id", Long.class);
        return DAOResult.getResult(result);
    }

    @Override
    public Reservation find(Long id) {
        List<Reservation> result = connectionDAOManager.query(
                new FindReservationPreparedStatementCreator(id), rowMapper);
        return DAOResult.getResult(result);
    }

    @Override
    public void remove(Long id) {
        connectionDAOManager.update(
                new RemoveReservationPreparedStatementCreator(id));
    }
}
