package reservation.respository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;
import reservation.model.dto.RequestReservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Repository
public class ReservationJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());

            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Reservation findReservationById(Long reservationId) {
        String sql = "SELECT id, date, time, name, theme_name, theme_desc, theme_price FROM reservation WHERE id = ?";
        try {
            return this.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
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

    public int deleteReservationById(Long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return this.jdbcTemplate.update(sql, reservationId);
    }

    // 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    public boolean existByDateTime(LocalDate date, LocalTime time) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation WHERE date = ? AND time = ?)";
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(sql, Boolean.class, date, time));
    }

    // 해당 id를 갖는 예약이 있는지 확인
    public boolean existById(Long id){
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation WHERE id = ?)";
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }
}
