package kakao.repository;

import domain.Reservation;
import domain.Theme;
import kakao.error.ErrorCode;
import kakao.error.exception.RecordNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationJDBCRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Reservation> customerRowMapper = (resultSet, rowNum) -> {
        Long id = resultSet.getLong("id");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime time = resultSet.getTime("time").toLocalTime();
        String name = resultSet.getString("name");
        String themeName = resultSet.getString("theme.name");
        String themeDesc = resultSet.getString("theme.desc");
        Integer themePrice = resultSet.getInt("theme.price");
        Long themeId = resultSet.getLong("theme.id");

        return Reservation.builder()
                .id(id)
                .date(date)
                .time(time)
                .name(name)
                .theme(new Theme(themeId, themeName, themeDesc, themePrice))
                .build();
    };

    public long save(Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public Reservation findById(Long id) {
        String SELECT_SQL = "select * from reservation join theme on reservation.theme_id=theme.id where reservation.id=? limit 1";

        List<Reservation> result = jdbcTemplate.query(SELECT_SQL, customerRowMapper, id);
        if (result.isEmpty()) throw new RecordNotFoundException(ErrorCode.RESERVATION_NOT_FOUND, null);
        return result.get(0);
    }

    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        String SELECT_SQL = "select * from reservation join theme on reservation.theme_id=theme.id where reservation.date=? and reservation.time=?";

        return jdbcTemplate.query(SELECT_SQL, customerRowMapper, date, time);
    }

    public List<Reservation> findByRequestId(Long requestId) {
        String SELECT_SQL = "select * from reservation join theme on reservation.theme_id=theme.id where reservation.theme_id=?";

        return jdbcTemplate.query(SELECT_SQL, customerRowMapper, requestId);
    }

    public int delete(Long id) {
        String DELETE_SQL = "delete from reservation where id=?";

        return jdbcTemplate.update(DELETE_SQL, id);
    }
}
