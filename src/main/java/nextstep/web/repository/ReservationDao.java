package nextstep.web.repository;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ReservationDao implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Theme theme = Theme.builder()
                .name(resultSet.getString("theme_name"))
                .desc(resultSet.getString("theme_desc"))
                .price(resultSet.getInt("theme_price"))
                .build();
        return Reservation.builder()
                .id(resultSet.getLong("id"))
                .date(resultSet.getDate("date")
                        .toLocalDate())
                .time(resultSet.getTime("time")
                        .toLocalTime())
                .name(resultSet.getString("name"))
                .theme(theme)
                .build();
    };

    public Long save(Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        Theme theme = reservation.getTheme();
        Map<String, Object> params = new HashMap<>();

        params.put("date", reservation.getDate());
        params.put("time", reservation.getTime());
        params.put("name", reservation.getName());
        params.put("theme_name", theme.getName());
        params.put("theme_desc", theme.getDesc());
        params.put("theme_price", theme.getPrice());

        Number key = simpleJdbcInsert.executeAndReturnKey(params);

        return key.longValue();
    }

    public Reservation findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE ID = ?;";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE ID = ?;";
        jdbcTemplate.update(sql, id);
    }
}
