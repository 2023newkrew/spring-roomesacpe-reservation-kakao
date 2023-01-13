package nextstep.reservations.domain.repository.reservation;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.exceptions.theme.exception.NoSuchThemeException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Primary
public class JdbcTemplateReservationRepository implements ReservationRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long add(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> getInsertOnePstmt(connection, reservation), keyHolder);
            return Objects.requireNonNull(keyHolder.getKey()).longValue();
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateReservationException();
        }
        catch (DataIntegrityViolationException e) {
            throw new NoSuchThemeException();
        }
    }

    @Override
    public Reservation findById(Long id) {
        RowMapper<Reservation> rowMapper = getReservationRowMapper();

        try {
            return Objects.requireNonNull(jdbcTemplate.queryForObject(ReservationQuery.FIND_BY_ID.get(), rowMapper, id));
        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchReservationException();
        }
    }

    @Override
    public void remove(final Long id) {
        int count = jdbcTemplate.update(ReservationQuery.REMOVE_BY_ID.get(), id);

        if (count == 0) throw new NoSuchReservationException();
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
