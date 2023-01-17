package nextstep.repository;

import static nextstep.repository.ReservationJdbcSql.DELETE_BY_ID_STATEMENT;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_DATE_AND_TIME_STATEMENT;
import static nextstep.repository.ReservationJdbcSql.FIND_BY_ID_STATEMENT;
import static nextstep.repository.ReservationJdbcSql.EXIST_BY_THEME_ID_STATEMENT;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    public Reservation save(Reservation reservation) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", Date.valueOf(reservation.getDate()));
        parameters.put("time", Time.valueOf(reservation.getTime()));
        parameters.put("name", reservation.getName());
        parameters.put("theme_id", reservation.getTheme().getId());
        long id = new SimpleJdbcInsert(jdbcTemplate).withTableName("RESERVATION").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters).longValue();
        return Reservation.creteReservation(reservation, id);
    }

    @Override
    public Optional<Reservation> findById(Long id) throws DataAccessException {
        return jdbcTemplate.query(FIND_BY_ID_STATEMENT,
                (rs, rowNum) -> Reservation.creteReservation(Reservation.builder()
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .name(rs.getString("name"))
                        .theme(Theme.builder()
                                .name(rs.getString("theme_name"))
                                .description(rs.getString("theme_desc"))
                                .price(rs.getInt("theme_price")).build()).build(), rs.getLong("id")),
                id).stream().findAny();
    }

    @Override
    public boolean existByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId)
            throws DataAccessException {
        return !jdbcTemplate.query(FIND_BY_DATE_AND_TIME_STATEMENT, (rs, rowNum) -> rs.getString(1), date, time, themeId)
                .isEmpty();
    }

    @Override
    public int deleteById(Long id) throws DataAccessException {
        return jdbcTemplate.update(DELETE_BY_ID_STATEMENT, id);
    }

    @Override
    public boolean existByThemeId(Long id) {
        return jdbcTemplate.query(EXIST_BY_THEME_ID_STATEMENT, (rs, rowNum) -> rs.getLong(1), id).size()!=0;
    }

}
