package roomescape.dao.reservation;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;
import roomescape.dto.Reservation;

public interface ReservationDAO {

    String ID_TABLE = "id";
    String DATE_TABLE = "date";
    String TIME_TABLE = "time";
    String NAME_TABLE = "name";
    String THEME_ID_TABLE = "theme_id";

    ResultSetExtractor<Reservation> reservationResultSetExtractor = rs -> {
        if (!rs.next()) {
            return null;
        }
        return new Reservation(
                rs.getLong(ID_TABLE), rs.getDate(DATE_TABLE).toLocalDate(),
                rs.getTime(TIME_TABLE).toLocalTime(), rs.getString(NAME_TABLE),
                rs.getLong(THEME_ID_TABLE));
    };
    ResultSetExtractor<Boolean> existResultSetExtractor = rs -> {
        if (!rs.next()) {
            return null;
        }
        return rs.getBoolean("result");
    };

    Boolean exist(@NonNull Reservation reservation);
    Boolean existId(long id);
    Boolean existThemeId(long id);
    Long create(@NonNull Reservation reservation);
    Reservation find(long id);
    void remove(long id);
}
