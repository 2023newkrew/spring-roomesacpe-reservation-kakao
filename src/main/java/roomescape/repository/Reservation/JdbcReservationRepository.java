package roomescape.repository.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.config.AppConfig;
import roomescape.domain.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class JdbcReservationRepository implements ReservationRepository{
    public final JdbcTemplate jdbcTemplate;
    private final H2JdbcDriver h2JdbcDriver;

    @Autowired
    public JdbcReservationRepository() throws ClassNotFoundException {
        AppConfig appConfig = new AppConfig();
        this.jdbcTemplate = new JdbcTemplate(appConfig.getDataSource());
        this.h2JdbcDriver = new H2JdbcDriver();
    }

    @Override
    public Long createReservation(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});
            ps.setObject(1, reservation.getDate());
            ps.setObject(2, reservation.getTime());
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getThemeId());
            return ps;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return h2JdbcDriver.createReserve(keyHolder);
    }

    @Override
    public Optional<Reservation> findReservationById(long reservationId) {
        String sql = "select date, time, name, theme_id, from RESERVATION where id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    LocalTime time = LocalTime.parse(rs.getString("time"));
                    String name = rs.getString("name");
                    Long themeId = rs.getLong("theme_id");
                    return new Reservation(reservationId, date, time, name, themeId);
                }, reservationId);
        return Optional.ofNullable(reservation);
    }

    @Override
    public Integer findCountByDateAndTime(Reservation reservation) {
        String sql = "select count(*) from RESERVATION where date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class,
                reservation.getDate(), reservation.getTime());
    }

    @Override
    public Integer deleteReservation(long deleteId) {
        String sql = "DELETE FROM RESERVATION WHERE id=?";
        return jdbcTemplate.update(sql, deleteId);
    }
}
