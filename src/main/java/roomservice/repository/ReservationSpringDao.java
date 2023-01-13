package roomservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomservice.domain.entity.Reservation;
import roomservice.domain.entity.Theme;
import roomservice.exceptions.exception.DuplicatedReservationException;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * ReservationSpringDao implements ReservationDao using spring-jdbc,
 * responsible for RESERVATION table.
 */
@Repository
public class ReservationSpringDao implements ReservationDao {
    private JdbcTemplate jdbcTemplate;
    private ThemeDao themeDao;

    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                new Theme(
                        null,
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                ));
        return reservation;
    };

    @Autowired
    public ReservationSpringDao(JdbcTemplate jdbcTemplate, ThemeDao themeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.themeDao = themeDao;
    }

    /**
     * conduct insertion query to DB
     *
     * @param reservation reservation to be added to DB.
     * @return id automatically given by DB.
     */
    public long insertReservation(Reservation reservation) {
        validateDuplication(reservation);
        String sql = "insert into RESERVATION (date, time, name, theme_name, theme_desc, theme_price)" +
                " values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getDate().toString());
            ps.setString(2, reservation.getTime().toString());
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void validateDuplication(Reservation reservation) {
        String sql = "select count(*) from RESERVATION WHERE date = ? and time = ?";
        if (jdbcTemplate.queryForObject(sql, Integer.class,
                reservation.getDate(), reservation.getTime()) > 0) {
            throw new DuplicatedReservationException();
        }
    }


    /**
     * conduct selection query to DB.
     *
     * @param id which you want to find.
     * @return reservation if found, null if not found.
     */
    public Reservation selectReservation(long id) {
        String sql = "select * from RESERVATION WHERE id = ?";
        List<Reservation> result = jdbcTemplate.query(sql, reservationRowMapper, id);
        if (result.size() == 0) {
            return null;
        }

        result.get(0).getTheme().setId(
                themeDao.selectThemeByName(result.get(0).getTheme().getName())
                        .getId());
        return result.get(0);
    }

    /**
     * conduct deletion query to DB.
     *
     * @param id which you want to delete.
     */
    public void deleteReservation(long id) {
        String sql = "delete from RESERVATION where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
