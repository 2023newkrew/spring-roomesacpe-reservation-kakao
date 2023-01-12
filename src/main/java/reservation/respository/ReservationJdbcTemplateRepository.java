package reservation.respository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import reservation.model.domain.Reservation;
import reservation.model.domain.Theme;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Repository
public class ReservationJdbcTemplateRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime())
                .addValue("name", reservation.getName())
                .addValue("theme_id", reservation.getThemeId());
        return this.jdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public Reservation findById(Long reservationId) {
        String sql = "SELECT id, date, time, name, theme_id FROM reservation WHERE id = ?";
        try {
            return this.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getLong("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    rs.getLong("theme_id")
            ), reservationId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return this.jdbcTemplate.update(sql, reservationId);
    }

    // 예약 생성 시 같은 테마의 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    public boolean existByDateTimeTheme(LocalDate date, LocalTime time, Long themeId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation WHERE date = ? AND time = ? AND theme_id = ?)";
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(sql, Boolean.class, date, time, themeId));
    }

    // 해당 id를 갖는 예약이 있는지 확인
    public boolean existById(Long id){
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation WHERE id = ?)";
        return Boolean.TRUE.equals(this.jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }

    public int deleteAllByThemeId(Long id) {
        String sql = "DELETE FROM reservation WHERE theme_id = ?";
        return this.jdbcTemplate.update(sql, id);
    }
}
