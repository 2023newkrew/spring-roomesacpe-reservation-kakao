package kakao.repository;

import javax.sql.DataSource;
import kakao.domain.Reservation;
import kakao.domain.Theme;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
@Profile("default")
public class ReservationJDBCRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    public ReservationJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Reservation> customerRowMapper = (resultSet, rowNum) -> {
        Long id = resultSet.getLong("id");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime time = resultSet.getTime("time").toLocalTime();
        String name = resultSet.getString("name");
        String themeName = resultSet.getString("theme_name");
        String themeDesc = resultSet.getString("theme_desc");
        Integer themePrice = resultSet.getInt("theme_price");
        return new Reservation(id, date, time, name, new Theme(themeName, themeDesc, themePrice));
    };

    public Reservation save(Reservation reservation) {
        try {
            // TODO : Theme 이 별도의 table 로 분리되면 BeanPropertySqlParameterSource 적용하기
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("date", reservation.getDate())
                    .addValue("time", reservation.getTime())
                    .addValue("name", reservation.getName())
                    .addValue("theme_name", reservation.getTheme().getName())
                    .addValue("theme_desc", reservation.getTheme().getDesc())
                    .addValue("theme_price", reservation.getTheme().getPrice());
            reservation.setId(jdbcInsert.executeAndReturnKey(params).longValue());
            return reservation;
        } catch (DuplicateKeyException e) {
            return null;
        }
    }

    public Reservation findById(Long id) {
        String SELECT_SQL = "select * from reservation where id=?";
        try {
            return jdbcTemplate.queryForObject(SELECT_SQL, customerRowMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        String SELECT_SQL = "select * from reservation where date=? and time=?";
        return jdbcTemplate.query(SELECT_SQL, customerRowMapper, date, time);
    }

    public int delete(Long id) {
        String DELETE_SQL = "delete from reservation where id=?";
        return jdbcTemplate.update(DELETE_SQL, id);
    }
}