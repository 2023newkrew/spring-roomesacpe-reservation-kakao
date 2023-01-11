package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                new Theme(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                )
        );
        return reservation;
    };

    @Override
    public Reservation findById(Long id) {
        String sql = "select * from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        validateReservation(date, time);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ps.setString(3, name);
            ps.setString(4, theme.getName());
            ps.setString(5, theme.getDesc());
            ps.setInt(6, theme.getPrice());
            return ps;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void validateReservation(LocalDate date, LocalTime time) {
        String sql = "select count(*) from reservation where date = ? and time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        if (count > 0) throw new IllegalArgumentException("이미 예약된 일시에는 예약이 불가능합니다.");
    }
}
