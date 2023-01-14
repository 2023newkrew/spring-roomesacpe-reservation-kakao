package roomescape.dao.reservation;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import roomescape.dto.Reservation;

public interface ReservationDAO {

    String ID_TABLE = "id";
    String DATE_TABLE = "date";
    String TIME_TABLE = "time";
    String NAME_TABLE = "name";
    String THEME_ID_TABLE = "theme_id";

    RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong(ID_TABLE), resultSet.getDate(DATE_TABLE).toLocalDate(),
            resultSet.getTime(TIME_TABLE).toLocalTime(), resultSet.getString(NAME_TABLE),
            resultSet.getLong(THEME_ID_TABLE));
    RowMapper<Boolean> existRowMapper = (resultSet, rowNum) -> resultSet.getBoolean(
            "result");

    Boolean exist(@NonNull Reservation reservation);
    Boolean existId(long id);
    Boolean existThemeId(long id);
    Long create(@NonNull Reservation reservation);
    Reservation find(long id);
    void remove(long id);
}
