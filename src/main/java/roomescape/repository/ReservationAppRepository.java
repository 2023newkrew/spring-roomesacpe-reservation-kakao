package roomescape.repository;

import roomescape.domain.Reservation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;
import roomescape.domain.Theme;

@Repository
public class ReservationAppRepository implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;
    private final RowMapper<Reservation> actorRowMapper = (rs, rowNum) -> {
        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(
                        rs.getLong("tid"),
                        rs.getString("theme_name"),
                        rs.getString("theme_desc"),
                        rs.getInt("theme_price")
                )
        );
        return reservation;
    };

    public ReservationAppRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long addReservation(Reservation reservation) {
        Map reservationInfo = Map.of("date", reservation.getDate(),
                "time", reservation.getTime(),
                "name", reservation.getName(),
                "theme_name", reservation.getTheme().getName(),
                "theme_desc", reservation.getTheme().getDesc(),
                "theme_price", reservation.getTheme().getPrice());
        return insertActor.executeAndReturnKey(reservationInfo).longValue();
    }

    @Override
    public int checkSchedule(String date, String time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE `date` = ? AND `time` = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, date, time);
    }

    @Override
    public Reservation findReservation(Long id) {
        String sql = "SELECT r.*, t.id AS tid FROM reservation r, theme t WHERE r.id = ? AND r.theme_name = t.name;";
        return jdbcTemplate.query(sql, actorRowMapper, id).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public int removeReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?;";
        return jdbcTemplate.update(sql, id);
    }

}
