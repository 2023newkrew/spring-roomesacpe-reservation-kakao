package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.theme.domain.Theme;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class ReservationRepository {

    private static final int INDEX_ID = 1;
    private static final int INDEX_DATE = 2;
    private static final int INDEX_TIME = 3;
    private static final int INDEX_NAME = 4;
    private static final int INDEX_THEME_NAME = 5;
    private static final int INDEX_THEME_DESC = 6;
    private static final int INDEX_THEME_PRICE = 7;
    final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Boolean findDuplicatedDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT EXISTS(SELECT * FROM RESERVATION WHERE date = ? AND time = ?);";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, time);
    }

    public Reservation findById(String reservationId){
        String sql = "SELECT * FROM RESERVATION WHERE id = ?;";
        Reservation reservation = jdbcTemplate.queryForObject(
                sql,
                (lookUpRs, rowNum) -> {
                    Long id = lookUpRs.getLong(INDEX_ID);
                    LocalDate date = LocalDate.parse(lookUpRs.getString(INDEX_DATE));
                    LocalTime time = LocalTime.parse(lookUpRs.getString(INDEX_TIME));
                    String name = lookUpRs.getString(INDEX_NAME);
                    String themeName = lookUpRs.getString(INDEX_THEME_NAME);
                    String themeDesc = lookUpRs.getString(INDEX_THEME_DESC);
                    Integer themePrice = lookUpRs.getInt(INDEX_THEME_PRICE);
                    Theme theme = new Theme(themeName, themeDesc, themePrice);
                    return new Reservation(id, date, time, name, theme);
                },
                Long.parseLong(reservationId));
        return reservation;
    }

    public void deleteById(String reservationId) {
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(sql, reservationId);
    }
}
