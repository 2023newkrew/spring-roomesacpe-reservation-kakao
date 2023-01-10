package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class ReservationWebRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationWebRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
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

    @Override
    public void insertReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                reservation.getTheme().getName(),
                reservation.getTheme().getDesc(),
                reservation.getTheme().getPrice()
        );
    }

    @Override
    public Optional<Reservation> getReservation(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM reservation WHERE date = (?) and time = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, date, time)
                .stream()
                .findAny();
    }
}
