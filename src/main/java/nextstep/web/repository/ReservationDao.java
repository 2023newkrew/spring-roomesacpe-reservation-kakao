package nextstep.web.repository;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.exception.BusinessException;
import nextstep.web.exception.CommonErrorCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Transactional
public class ReservationDao implements ReservationRepository {

    public static final String TABLE_NAME = "reservation";
    public static final String KEY_COLUMN_NAME = "id";
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> Reservation.from(resultSet);

    public Long save(Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN_NAME);

        Number key = simpleJdbcInsert.executeAndReturnKey(prepareParams(reservation));

        return key.longValue();
    }


    public Reservation findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE ID = ?;";
        List<Reservation> reservations = jdbcTemplate.query(sql, actorRowMapper, id);
        if (reservations.isEmpty()) {
            throw new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }

        return reservations.get(0);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE ID = ?;";
        if (jdbcTemplate.update(sql, id) == 0) {
            throw new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    private static Map<String, Object> prepareParams(Reservation reservation) {
        Theme theme = reservation.getTheme();

        return Map.of(
                "date", reservation.getDate(),
                "time", reservation.getTime(),
                "name", reservation.getName(),
                "theme_name", theme.getName(),
                "theme_desc", theme.getDesc(),
                "theme_price", theme.getPrice()
        );
    }
}
