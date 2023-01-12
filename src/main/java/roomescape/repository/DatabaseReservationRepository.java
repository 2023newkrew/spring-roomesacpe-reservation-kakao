package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import java.time.format.DateTimeFormatter;
import roomescape.exceptions.exception.DuplicatedReservationException;

@Repository
public class DatabaseReservationRepository implements ReservationRepository{
    private final JdbcTemplate jdbcTemplate;

    public DatabaseReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Reservation reservation) {
        if (isReservationIdDuplicated(reservation)) {
            throw new DuplicatedReservationException();
        }
        String sql = "insert into RESERVATION (date, time, name) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            ps.setString(2, reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            ps.setString(3, reservation.getName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = "select * from RESERVATION WHERE id = ?";
        Reservation reservation;
        try {
            reservation = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> Reservation.builder()
                    .id(resultSet.getLong("id"))
                    .date(resultSet.getDate("date").toLocalDate())
                    .time(resultSet.getTime("time").toLocalTime())
                    .name(resultSet.getString("name"))
                    .build(), id);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(reservation);
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from RESERVATION where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private boolean isReservationIdDuplicated(Reservation reservation) {
        String sql = "select count(*) from RESERVATION WHERE date =  ? and time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, reservation.getDate(), reservation.getTime()) > 0;
    }
}
