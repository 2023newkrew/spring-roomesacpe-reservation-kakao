package nextstep.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    public void delete(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Long add(Reservation reservation) {
        try {
            return (long) jdbcTemplate.update(
                    "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) values (?, ?, ?, ?, ?, ?)",
                    reservation.getDate(),
                    reservation.getTime(),
                    reservation.getName(),
                    reservation.getTheme().getName(),
                    reservation.getTheme().getDesc(),
                    reservation.getTheme().getPrice()
            );
        } catch (DuplicateKeyException e) {
            throw new DuplicateReservationException();
        }
    }

    public Reservation getReservation(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM reservation WHERE id = ?",
                (rs, rowNum)  -> Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .theme(Theme.builder()
                        .name(rs.getString("theme_name"))
                        .desc(rs.getString("theme_desc"))
                        .price(rs.getInt("theme_price"))
                        .build())
                .build(),
                id
        );
    }

}
