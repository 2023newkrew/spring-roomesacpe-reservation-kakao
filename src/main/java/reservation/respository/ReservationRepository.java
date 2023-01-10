package reservation.respository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import reservation.domain.Reservation;
import reservation.domain.Theme;
import reservation.domain.dto.ReservationDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createReservation(ReservationDto reservationDto, Theme theme) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservationDto.getDate()));
            ps.setTime(2, Time.valueOf(reservationDto.getTime()));
            ps.setString(3, reservationDto.getName());
            ps.setString(4, theme.getName());
            ps.setString(5, theme.getDesc());
            ps.setInt(6, theme.getPrice());

            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Reservation getReservation(Long reservationId) {
        String sql = "SELECT id, date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getLong("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    new Theme(rs.getString("theme_name"),
                            rs.getString("theme_desc"),
                            rs.getInt("theme_price"))
            ), reservationId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deleteReservation(Long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, reservationId);
    }

    // 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    public boolean existReservation(LocalDate date, LocalTime time) {
        String sql = "SELECT EXISTS(SELECT 1 FROM reservation WHERE date = ? AND time = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, date, time));
    }
}
