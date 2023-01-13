package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class ReservationWebRepository implements CrudRepository<Reservation, Long>{
    private final JdbcTemplate jdbcTemplate;

    public ReservationWebRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name"),
            new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("theme_name"),
                    resultSet.getString("theme_desc"),
                    resultSet.getInt("theme_price")
            )
    );

    @Override
    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());

            return ps;
        }, keyHolder);

        return (Long) keyHolder.getKey();
    }

    @Override
    public Optional<Reservation> findOne(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = (?)";

        return jdbcTemplate.query(sql, reservationRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Reservation> findReservationByDateAndTime(LocalDate date, LocalTime time){
        String sql = "SELECT * FROM reservation WHERE date = (?) and time = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, date, time)
                .stream()
                .findAny();
    }
}