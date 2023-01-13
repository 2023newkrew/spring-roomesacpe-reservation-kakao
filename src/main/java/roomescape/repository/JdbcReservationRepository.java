package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository{
    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReservationRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long createReservation(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (date, time, name) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setObject(1, reservation.getDate());
            ps.setObject(2, reservation.getTime());
            ps.setString(3, reservation.getName());
            return ps;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Reservation> findById(int reservationId) {
        String sql = "select date, time, name, theme_name, theme_desc, theme_price from RESERVATION where id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql,
                new Object[]{reservationId},
                (rs, rowNum) -> {
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    LocalTime time = LocalTime.parse(rs.getString("time"));
                    String name = rs.getString("name");
                    String themeName = rs.getString("theme_name");
                    String themeDesc = rs.getString("theme_desc");
                    Integer themePrice = rs.getInt("theme_price");
                    return new Reservation((long) reservationId, date, time, name, themeName, themeDesc, themePrice);
                });
        return Optional.ofNullable(reservation);
    }

    @Override
    public Integer findByDateAndTime(Reservation reservation) {
        String sql = "select count(*) from RESERVATION where date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                reservation.getDate(), reservation.getTime());
    }

    @Override
    public Integer deleteReservation(int deleteId) {
        String sql = "DELETE FROM RESERVATION WHERE id=?";
        return jdbcTemplate.update(sql, deleteId);
    }
}
