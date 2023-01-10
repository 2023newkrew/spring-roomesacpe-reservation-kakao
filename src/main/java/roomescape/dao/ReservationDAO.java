package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;
import roomescape.dto.Theme;

@Repository
public class ReservationDAO {

    public static final String ID_TABLE = "id";
    public static final String DATE_TABLE = "date";
    public static final String TIME_TABLE = "time";
    public static final String NAME_TABLE = "name";
    public static final String THEME_NAME_TABLE = "theme_name";
    public static final String THEME_DESC_TABLE = "theme_desc";
    public static final String THEME_PRICE_TABLE = "theme_price";

    private static final String FIND_SQL = String.format(
            "SELECT * FROM reservation WHERE %s = :%s",
            ID_TABLE, ID_TABLE);
    private static final String FIND_BY_DATETIME_SQL = String.format(
            "SELECT count(*) FROM reservation WHERE %s = :%s and %s = :%s",
            DATE_TABLE, DATE_TABLE, TIME_TABLE, TIME_TABLE);
    private static final String ADD_SQL = String.format(
            "INSERT INTO reservation(%s, %s, %s, %s, %s, %s) "
                    + "VALUES (:%s, :%s, :%s, :%s, :%s, :%s)",
            DATE_TABLE, TIME_TABLE, NAME_TABLE, THEME_NAME_TABLE, THEME_DESC_TABLE, THEME_PRICE_TABLE,
            DATE_TABLE, TIME_TABLE, NAME_TABLE, THEME_NAME_TABLE, THEME_DESC_TABLE, THEME_PRICE_TABLE);
    private static final String DELETE_SQL = String.format(
            "DELETE FROM reservation where %s = :%s",
            ID_TABLE, ID_TABLE);

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong(ID_TABLE),
            resultSet.getDate(DATE_TABLE).toLocalDate(),
            resultSet.getTime(TIME_TABLE).toLocalTime(),
            resultSet.getString(NAME_TABLE),
            new Theme(
                    resultSet.getString(THEME_NAME_TABLE),
                    resultSet.getString(THEME_DESC_TABLE),
                    resultSet.getInt(THEME_PRICE_TABLE)));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addReservation(Reservation reservation) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                Map.of(
                        DATE_TABLE, reservation.getDate(),
                        TIME_TABLE, reservation.getTime(),
                        NAME_TABLE,reservation.getName(),
                        THEME_NAME_TABLE, reservation.getTheme().getName(),
                        THEME_DESC_TABLE, reservation.getTheme().getDesc(),
                        THEME_PRICE_TABLE,reservation.getTheme().getPrice()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(ADD_SQL, namedParameters, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    public Reservation findReservation(long id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                ID_TABLE, id);
        return namedParameterJdbcTemplate.queryForObject(FIND_SQL, sqlParameterSource, actorRowMapper);
    }

    public Long findCountReservationByDateTime(LocalDate date, LocalTime time) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                Map.of(
                        DATE_TABLE, date,
                        TIME_TABLE, time));
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_DATETIME_SQL,sqlParameterSource, Long.class);
    }

    public void deleteReservation(long id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                ID_TABLE, id);
        namedParameterJdbcTemplate.update(DELETE_SQL, sqlParameterSource);
    }
}
