package roomescape.dao.reservation;

import org.springframework.jdbc.core.RowMapper;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

public abstract class ReservationDAO {

    private static final String ID_TABLE = "id";
    private static final String DATE_TABLE = "date";
    private static final String TIME_TABLE = "time";
    private static final String NAME_TABLE = "name";
    private static final String THEME_ID_TABLE = "theme_id";

    private static final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong(ID_TABLE), resultSet.getDate(DATE_TABLE).toLocalDate(),
            resultSet.getTime(TIME_TABLE).toLocalTime(), resultSet.getString(NAME_TABLE),
            resultSet.getLong(THEME_ID_TABLE));
    private static final RowMapper<Boolean> existRowMapper = (resultSet, rowNum) -> resultSet.getBoolean(
            "result");

    protected void validate(Reservation reservation) {
        if (reservation.getDate() == null || reservation.getTime() == null
                || reservation.getName() == null) {
            throw new BadRequestException();
        }
        if (exist(reservation)) {
            throw new BadRequestException();
        }
    }

    protected static RowMapper<Reservation> getRowMapper() {
        return rowMapper;
    }

    protected static RowMapper<Boolean> getExistRowMapper() {
        return existRowMapper;
    }

    public abstract boolean exist(Reservation reservation);

    public abstract Long create(Reservation reservation);

    public abstract Reservation find(Long id);

    public abstract void remove(Long id);
}
