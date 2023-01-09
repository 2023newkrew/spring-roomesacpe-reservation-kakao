package kakao.repository;

import kakao.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.Objects;

@Repository
public class ReservationJDBCRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Reservation reservation) {
        String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        System.out.println(reservation.getTheme().getName());
        System.out.println(reservation.getTheme().getDesc());
        System.out.println(reservation.getTheme().getPrice());
        
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[]{"ID"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());

            return ps;
        }, keyHolder);

        if (Objects.isNull(keyHolder.getKey())) {
            return -1;
        }
        return keyHolder.getKey().longValue();
    }

}
