package roomescape.dao.reservation;

import java.util.List;
import org.springframework.lang.NonNull;
import roomescape.dao.DAOManager;
import roomescape.dao.DAOResult;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationThemeIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
import roomescape.dto.Reservation;

public class ConsoleReservationDAO implements ReservationDAO {

    private final DAOManager daoManager;

    public ConsoleReservationDAO(String url, String user, String password) {
        daoManager = new DAOManager(url, user, password);
    }

    @Override
    public Boolean exist(@NonNull Reservation reservation) {
        try {
            List<Boolean> result = daoManager.query(
                    new ExistReservationPreparedStatementCreator(reservation), existRowMapper);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean existId(long id) {
        try {
            List<Boolean> result = daoManager.query(
                    new ExistReservationIdPreparedStatementCreator(id), existRowMapper);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean existThemeId(long id) {
        try {
            List<Boolean> result = daoManager.query(
                    new ExistReservationThemeIdPreparedStatementCreator(id), existRowMapper);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long create(@NonNull Reservation reservation) {
        try {
            List<Long> result = daoManager.updateAndGetKey(
                    new InsertReservationPreparedStatementCreator(reservation), "id", Long.class);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Reservation find(long id) {
        try {
            List<Reservation> result = daoManager.query(
                    new FindReservationPreparedStatementCreator(id), rowMapper);
            return DAOResult.getResult(result);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(long id) {
        try {
            daoManager.update(
                    new RemoveReservationPreparedStatementCreator(id));
        } catch (Exception ignored) {

        }
    }
}
