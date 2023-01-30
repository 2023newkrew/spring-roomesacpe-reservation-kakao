package roomescape.dao.reservation;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;
import roomescape.dto.Reservation;

public interface ReservationDAO {

    String ID_COLUMN = "id";
    String DATE_COLUMN = "date";
    String TIME_COLUMN = "time";
    String NAME_COLUMN = "name";
    String THEME_ID_COLUMN = "theme_id";

    ResultSetExtractor<Reservation> reservationResultSetExtractor = rs -> {
        if (!rs.next()) {
            return null;
        }
        return new Reservation(
                rs.getLong(ID_COLUMN), rs.getDate(DATE_COLUMN).toLocalDate(),
                rs.getTime(TIME_COLUMN).toLocalTime(), rs.getString(NAME_COLUMN),
                rs.getLong(THEME_ID_COLUMN));
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
