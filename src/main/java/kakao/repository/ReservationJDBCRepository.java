package kakao.repository;

import kakao.domain.Reservation;
import kakao.domain.Theme;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
public class ReservationJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public long save(Reservation reservation) {
        String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"ID"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());

            return ps;
        }, keyHolder);

        if (Objects.isNull(keyHolder.getKey())) {
            return -1;
        }
        return keyHolder.getKey().longValue();
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
