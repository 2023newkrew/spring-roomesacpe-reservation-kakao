package nextstep.reservations.domain.repository.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class WebReservationRepository implements ReservationRepository{
    private final JdbcTemplate jdbcTemplate;

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
    }

    @Override
    public Reservation findById(Long id) {
        RowMapper<Reservation> rowMapper = getReservationRowMapper();
        return jdbcTemplate.queryForObject(ReservationQuery.FIND_BY_ID.get(), rowMapper, id);
    }

    @Override
    public void remove(final Long id) {
        jdbcTemplate.update(ReservationQuery.REMOVE_BY_ID.get(), id);
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
