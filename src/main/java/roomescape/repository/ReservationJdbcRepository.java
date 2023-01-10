package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import javax.sql.DataSource;
import java.sql.Date;
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
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                resultSet.getString("theme_name")
        );
        return reservation;
    };

    @Override
    public long save(Reservation reservation) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time", reservation.getTime().toString());
        parameters.put("name", reservation.getName());
        parameters.put("theme_name", "워너고홈");
        parameters.put("theme_desc", "병맛 어드벤처 회사 코믹물");
        parameters.put("theme_price", "29000");
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Reservation> findOneById(long reservationId) {
        String sql = "select * from reservation where id = ? limit 1";
        return jdbcTemplate.query(sql, actorRowMapper, reservationId).stream().findFirst();
    }

    @Override
    public void delete(long reservationId) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, reservationId);
    }

    @Override
    public Boolean hasOneByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "select * from reservation where date = ? and time = ?";
        return jdbcTemplate.query(sql, actorRowMapper, date, time).size() > 0;
    }
}
