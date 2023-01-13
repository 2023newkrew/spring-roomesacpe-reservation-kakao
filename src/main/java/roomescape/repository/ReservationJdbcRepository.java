package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Map;
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
                    resultSet.getTimestamp("datetime").toLocalDateTime(),
                    resultSet.getString("name"),
                    resultSet.getLong("theme_id")
            )
    );

    @Override
    public Long save(Reservation reservation) {
        Map<String, String> parameters = Map.of(
                "datetime", reservation.getDateTime().toString(),
                "name", reservation.getName(),
                "theme_id", reservation.getThemeId().toString()
        );
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
    public Boolean existsByDateTime(LocalDateTime dateTime) {
        String sql = "select * from reservation where datetime = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, dateTime).size() > 0;
    }
}
