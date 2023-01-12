package roomescape.dao;

import nextstep.Reservation;
import roomescape.dto.ReservationRequest;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import nextstep.Theme;

@Repository
public class ReservationDAO {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;

    public ReservationDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        Map reservationInfo = Map.of("date", reservationRequest.getDate(),
                "time", reservationRequest.getTime(),
                "name", reservationRequest.getName(),
                "theme_name", "워너고홈",
                "theme_desc", "병맛 어드벤처 회사 코믹물",
                "theme_price", 29000);
        Long key = insertActor.executeAndReturnKey(reservationInfo).longValue();
        return reservationRequest.toReservation(key);
    }

    public int checkSchedule(ReservationRequest reservationRequest) {
        String sql = "select count(*) from reservation where `date` = ? and `time` = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, reservationRequest.getDate(), reservationRequest.getTime());
    }

    public List<Reservation> findReservation(Long id) {
        String sql = "select id, date, time, name, theme_name, theme_desc, theme_price from reservation where id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                new Theme(
                    rs.getString("theme_name"),
                    rs.getString("theme_desc"),
                    rs.getInt("theme_price")
                )
        ), id);
    }

    public int removeReservation(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
