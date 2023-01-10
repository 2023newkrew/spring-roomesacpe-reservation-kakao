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

    private static final String FIND_SQL = "select * from reservation where id = :id";
    private static final String FIND_BY_DATETIME_SQL = "select count(*) from reservation where date = :date and time = :time";
    private static final String ADD_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (:date, :time, :name, :theme_name, :theme_desc, :theme_price);";
    private static final String DELETE_SQL = "DELETE FROM reservation where id = :id";

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(), resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                new Theme(resultSet.getString("theme_name"), resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")));
        return reservation;
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long addReservation(Reservation reservation) {
        SqlParameterSource namedParameters = new MapSqlParameterSource(
                Map.of("date", reservation.getDate(), "time", reservation.getTime(), "name",
                        reservation.getName(), "theme_name", reservation.getTheme().getName(),
                        "theme_desc", reservation.getTheme().getDesc(), "theme_price",
                        reservation.getTheme().getPrice()));

        //        Long id = insertActor.executeAndReturnKey(parameters).longValue();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(ADD_SQL, namedParameters, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    public Reservation findReservation(long id) {
        return namedParameterJdbcTemplate.queryForObject(FIND_SQL,
                new MapSqlParameterSource("id", id), actorRowMapper);
    }

    public Long findCountReservationByDateTime(LocalDate date, LocalTime time) {
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_DATETIME_SQL,
                new MapSqlParameterSource(Map.of("date", date, "time", time)), Long.class);
    }

    public void deleteReservation(long id) {
        namedParameterJdbcTemplate.update(DELETE_SQL, new MapSqlParameterSource("id", id));
    }
}
