package roomservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomservice.domain.Reservation;
import roomservice.exceptions.exception.DuplicatedReservationException;
import roomservice.exceptions.exception.NonExistentReservationException;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * ReservationSpringDao implements ReservationDao using spring-jdbc.
 */
@Repository
public class ReservationSpringDao implements ReservationDao {
    private JdbcTemplate jdbcTemplate;
    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getLong("id"));
        reservation.setName(resultSet.getString("name"));
        reservation.setDate(resultSet.getDate("date").toLocalDate());
        reservation.setTime(resultSet.getTime("time").toLocalTime());
//                        테마 관련 - 다음 리뷰 요청 때 구현
//                        reservation.setTheme(new Theme(
//                                resultSet.getString("theme_name"),
//                                resultSet.getString("theme_desc"),
//                                resultSet.getInt("theme_price")
//                        ));

        return reservation;
    };

    @Autowired
    public ReservationSpringDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insertReservation(Reservation reservation) {
        validateDuplication(reservation);
        String sql = "insert into RESERVATION (date, time, name) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getDate().toString());
            ps.setString(2, reservation.getTime().toString());
            ps.setString(3, reservation.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Reservation selectReservation(long id) {
        String sql = "select * from RESERVATION WHERE id = ?";
        List<Reservation> result = jdbcTemplate.query(sql, reservationRowMapper, id);
        return result.size()==0 ? null : result.get(0);
    }

    public void deleteReservation(long id) {
        String sql = "delete from RESERVATION where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    private void validateDuplication(Reservation reservation) {
        String sql = "select count(*) from RESERVATION WHERE date = ? and time = ?";
        if (jdbcTemplate.queryForObject(sql, Integer.class,
                reservation.getDate(), reservation.getTime()) > 0) {
            throw new DuplicatedReservationException();
        }
    }
}
