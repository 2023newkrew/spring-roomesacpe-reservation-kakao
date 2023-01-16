package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationWebRepository implements CrudRepository<Reservation, Long>{
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name"),
            resultSet.getLong("theme_id")
    );

    @Override
    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme_id());

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

    public Optional<Reservation> findReservationByDateAndTimeAndTheme(LocalDate date, LocalTime time, Long themeId){
        String sql = "SELECT * FROM reservation WHERE date = (?) and time = (?) and theme_id = (?)";

        return jdbcTemplate.query(sql, reservationRowMapper, date, time, themeId)
                .stream()
                .findAny();
    }

    public List<Reservation> findAllByTheme_id(Long theme_id){
        String sql = "SELECT * FROM reservation WHERE theme_id = (?)";

        return jdbcTemplate.query(sql, reservationRowMapper, theme_id);
    }

}
