package nextstep.web.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.domain.Reservation;
import nextstep.web.common.exception.BusinessException;
import nextstep.web.common.exception.CommonErrorCode;
import nextstep.web.common.repository.RoomEscapeRepository;
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
public class ReservationDao implements RoomEscapeRepository<Reservation> {

    public static final String TABLE_NAME = "reservation";
    public static final String KEY_COLUMN_NAME = "id";
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> Reservation.from(resultSet);

    public Long save(Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN_NAME);

        Number key = simpleJdbcInsert.executeAndReturnKey(prepareParams(reservation));

        return key.longValue();
    }


    public Reservation findById(Long id) {
        String sql = "SELECT * FROM RESERVATION WHERE ID = ?;";
        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper, id);
        if (reservations.isEmpty()) {
            throw new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }

        return reservations.get(0);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?;";
        if (jdbcTemplate.update(sql, id) == 0) {
            throw new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    private Map<String, Object> prepareParams(Reservation reservation) {
        return Map.of(
                "date", reservation.getDate(),
                "time", reservation.getTime(),
                "name", reservation.getName(),
                "theme_id", reservation.getThemeId()
        );
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM THEME;";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
