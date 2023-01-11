package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.domain.Reservation;
import nextstep.roomescape.reservation.domain.Theme;
import nextstep.roomescape.reservation.exception.CreateReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
@Primary
public class ReservationRepositoryJdbcImpl implements ReservationRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation create(Reservation reservation) {
        Theme theme = reservation.getTheme();
        if (findByDateTime(reservation.getDate(), reservation.getTime())) {
            throw new CreateReservationException();
        }
        String sql = "insert into reservation (date, time, name, theme_name, theme_desc, theme_price) values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, theme.getName());
            ps.setString(5, theme.getDesc());
            ps.setInt(6, theme.getPrice());

            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation.getDate(), reservation.getTime(), reservation.getName(), theme);
    }

    @Override
    public Reservation findById(long id) {
        String sql = "select * from reservation where id= ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime(),
                            resultSet.getString("name"),
                            new Theme(resultSet.getString("theme_name"),
                                    resultSet.getString("theme_desc"),
                                    resultSet.getInt("theme_price"))
                    );
                    return reservation;
                }, id);
    }

    @Override
    public Boolean findByDateTime(LocalDate date, LocalTime time) {
        String sql = "select exists (select * from reservation where date= ? and time = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, time);
    }

    @Override
    public Boolean delete(long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id)) == 1 ;
    }

    @Override
    public void clear() {
        String sql = "delete from reservation";
        jdbcTemplate.update(sql);
    }

}