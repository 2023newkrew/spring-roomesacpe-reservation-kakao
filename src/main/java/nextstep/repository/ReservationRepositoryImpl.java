package nextstep.repository;

import static nextstep.repository.ReservationJdbcSql.DELETE_BY_ID;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_DATE_AND_TIME;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_ID;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import nextstep.dto.ReservationRequestDTO;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(ReservationRequestDTO reservationRequestDTO) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", Date.valueOf(reservationRequestDTO.getDate()));
        parameters.put("time", Time.valueOf(reservationRequestDTO.getTime()));
        parameters.put("name", reservationRequestDTO.getName());
        parameters.put("theme_id", reservationRequestDTO.getThemeId());
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("RESERVATION").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Reservation> findById(Long id) throws DataAccessException {
        return jdbcTemplate.query(FIND_BY_ID,
                (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime(), rs.getString("name"),
                        new Theme(rs.getString("theme_name"), rs.getString("theme_desc"), rs.getInt("theme_price"))),
                id).stream().findAny();
    }

    @Override
    public boolean existByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId)
            throws DataAccessException {
        return !jdbcTemplate.query(FIND_BY_DATE_AND_TIME, (rs, rowNum) -> rs.getString(1), date, time, themeId)
                .isEmpty();
    }

    @Override
    public int deleteById(Long id) throws DataAccessException {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }
}
