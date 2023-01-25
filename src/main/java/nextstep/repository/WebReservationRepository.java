package nextstep.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import nextstep.model.Reservation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WebReservationRepository implements ReservationRepository {
    private static final RowMapper<Reservation> ROW_MAPPER = new ReservationRowMapper();
    private final JdbcTemplate jdbcTemplate;

    public WebReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";

        PreparedStatementCreator creator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setObject(4, reservation.getTheme().getId());
            return ps;
        };

        jdbcTemplate.update(creator, keyHolder);
        Long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName(),
                reservation.getTheme());
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        try {
            String sql =
                    "SELECT r.id reservation_id, r.date reservation_date, r.time reservation_time, r.name reservation_name, "
                            + "t.name theme_name, t.desc theme_desc, t.price theme_price, t.id theme_id "
                            + "FROM reservation r JOIN theme t ON r.theme_id = t.id "
                            + "WHERE r.id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByDateAndTimeAndThemeId(LocalDate date, LocalTime time, Long themeId) {
        String sql = "SELECT count(*) FROM reservation WHERE date=? AND time=? AND theme_id=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time, themeId);
        return count > 0;
    }

    public void deleteAll() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }
}
