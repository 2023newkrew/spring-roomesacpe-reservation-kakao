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

    private static final String ID_TABLE = "id";
    private static final String DATE_TABLE = "date";
    private static final String TIME_TABLE = "time";
    private static final String NAME_TABLE = "name";
    private static final String THEME_NAME_TABLE = "theme_name";
    private static final String THEME_DESC_TABLE = "theme_desc";
    private static final String THEME_PRICE_TABLE = "theme_price";

    private static final String FIND_SQL =
            "SELECT * FROM reservation WHERE id = :id;";
    private static final String FIND_BY_DATETIME_SQL =
            "SELECT count(*) FROM reservation WHERE date = :date and time = :time;";
    private static final String ADD_SQL =
            "INSERT INTO reservation( date, time, name, theme_name, theme_desc, theme_price) "
                    + "VALUES (:date, :time, :name, :theme_name, :theme_desc, :theme_price);";
    private static final String DELETE_SQL =
            "DELETE FROM reservation where id = :id;";

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
        return namedParameterJdbcTemplate.queryForObject(
                FIND_SQL, sqlParameterSource, actorRowMapper);
    }

    public Long findCountReservationByDateTime(LocalDate date, LocalTime time) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                Map.of(
                        DATE_TABLE, date,
                        TIME_TABLE, time));
        return namedParameterJdbcTemplate.queryForObject(
                FIND_BY_DATETIME_SQL,sqlParameterSource, Long.class);
    }

    public void deleteReservation(long id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                ID_TABLE, id);
        namedParameterJdbcTemplate.update(DELETE_SQL, sqlParameterSource);
    }
}
