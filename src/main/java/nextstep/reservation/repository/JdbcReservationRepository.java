package nextstep.reservation.repository;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.ReservationException;
import nextstep.reservation.exception.ReservationExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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

import static nextstep.reservation.exception.ReservationExceptionCode.DUPLICATE_TIME_RESERVATION;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation create(Reservation reservation) {
        if (findByDateTime(reservation.getDate(), reservation.getTime())) {
            throw new ReservationException(DUPLICATE_TIME_RESERVATION);
        }

        String sql = "insert into reservation (date, time, name, theme_id) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation.getDate(), reservation.getTime(), reservation.getName(), reservation.getThemeId());
    }

    @Override
    public Reservation findById(long id) {
        String sql = "select * from reservation where id= ?";
        List<Reservation> reservationList = jdbcTemplate.query(sql, reservationRowMapper(), id);

        Reservation reservation = reservationList.stream().findAny().orElse(null);
        if (reservation == null) {
            throw new ReservationException(ReservationExceptionCode.NO_SUCH_RESERVATION);
        }
        return reservationList.stream().findAny().orElse(null);
    }

    @Override
    public Boolean findByDateTime(LocalDate date, LocalTime time) {
        String sql = "select exists (select * from reservation where date= ? and time = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, time);
    }

    @Override
    public Boolean delete(long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }

    @Override
    public void clear() {
        String sql = "delete from reservation";
        jdbcTemplate.update(sql);
    }

    public RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                resultSet.getLong("theme_id"));
    }

}
