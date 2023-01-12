package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;


@Repository
public class ReservationJdbcRepository implements ReservationRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> (
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime(),
                    resultSet.getString("name"),
                    resultSet.getLong("theme_id")
            )
    );

    @Override
    public Long save(Reservation reservation) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time", reservation.getTime().toString());
        parameters.put("name", reservation.getName());
        parameters.put("theme_id", reservation.getThemeId().toString());
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Reservation> find(Long id) {
        String sql = "select * from reservation where id = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, id).stream().findFirst();
    }

    @Override
    public Integer delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Boolean has(LocalDate date, LocalTime time) {
        String sql = "select * from reservation where date = ? and time = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, date, time).size() > 0;
    }
}
