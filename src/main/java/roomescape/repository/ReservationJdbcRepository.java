package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.Theme;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("reservation.id"),
                resultSet.getDate("reservation.date").toLocalDate(),
                resultSet.getTime("reservation.time").toLocalTime(),
                resultSet.getString("reservation.name"),
                new Theme(
                        resultSet.getLong("theme.id"),
                        resultSet.getString("theme.name"),
                        resultSet.getString("theme.desc"),
                        resultSet.getInt("theme.price")
                )
        );
    };

    @Override
    public long save(Reservation reservation) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time", reservation.getTime().toString());
        parameters.put("name", reservation.getName());
        parameters.put("theme_id", reservation.getTheme().getId().toString());
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Reservation> findOneById(long reservationId) {
        String sql = "select * from reservation join theme on reservation.theme_id = theme.id where reservation.id = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, reservationId).stream().findFirst();
    }

    @Override
    public void delete(long reservationId) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, reservationId);
    }

    @Override
    public Boolean hasOneByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "select * from reservation join theme on reservation.theme_id = theme.id where reservation.date = ? and reservation.time = ?";
        return jdbcTemplate.query(sql, actorRowMapper, date, time).size() > 0;
    }

    @Override
    public Boolean hasReservationOfTheme(long themeId) {
        String sql = "select * from reservation join theme on reservation.theme_id = theme.id where reservation.theme_id = ?";
        return jdbcTemplate.query(sql, actorRowMapper, themeId).size() > 0;
    }
}
