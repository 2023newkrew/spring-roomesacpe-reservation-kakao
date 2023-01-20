package roomescape.dao.reservation;

import org.springframework.lang.NonNull;
import roomescape.connection.ConnectionManager;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.ExistReservationThemeIdPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.FindReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.InsertReservationPreparedStatementCreator;
import roomescape.dao.reservation.preparedstatementcreator.RemoveReservationPreparedStatementCreator;
import roomescape.dto.Reservation;

public class ConsoleReservationDAO implements ReservationDAO {

    private final ConnectionManager connectionManager;

    public ConsoleReservationDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Boolean exist(Reservation reservation) {
        try {
            return connectionManager.query(
                    new ExistReservationPreparedStatementCreator(reservation),
                    existResultSetExtractor);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean existId(long id) {
        try {
            return connectionManager.query(
                    new ExistReservationIdPreparedStatementCreator(id),
                    existResultSetExtractor);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public Boolean existThemeId(long id) {
        try {
            return connectionManager.query(
                    new ExistReservationThemeIdPreparedStatementCreator(id),
                    existResultSetExtractor);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long create(@NonNull Reservation reservation) {
        try {
            return connectionManager.updateAndGetKey(
                    new InsertReservationPreparedStatementCreator(reservation),
                    "id", Long.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Reservation find(long id) {
        try {
            return connectionManager.query(
                    new FindReservationPreparedStatementCreator(id), reservationResultSetExtractor);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(long id) {
        try {
            connectionManager.update(
                    new RemoveReservationPreparedStatementCreator(id));
        } catch (Exception ignored) {

        }
    }
}
