package nextstep.reservation.repository;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import nextstep.reservation.exceptions.exception.DuplicateReservationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public void delete(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Long add(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con -> {
                PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
                pstmt.setDate(1, Date.valueOf(reservation.getDate()));
                pstmt.setTime(2, Time.valueOf(reservation.getTime()));
                pstmt.setString(3, reservation.getName());
                pstmt.setString(4, reservation.getTheme().getName());
                pstmt.setString(5, reservation.getTheme().getDesc());
                pstmt.setInt(6, reservation.getTheme().getPrice());
                return pstmt;
            }, keyHolder);

            return keyHolder.getKey().longValue();
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateReservationException();
        }
    }

    public Reservation getReservation(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        RowMapper<Reservation> rowMapper = getReservationRowMapper();
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private static RowMapper<Reservation> getReservationRowMapper() {
        return (rs, rowNum) -> Reservation.builder()
                .id(rs.getLong("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .name(rs.getString("name"))
                .theme(Theme.builder()
                        .name(rs.getString("theme_name"))
                        .desc(rs.getString("theme_desc"))
                        .price(rs.getInt("theme_price"))
                        .build())
                .build();
    }
}
