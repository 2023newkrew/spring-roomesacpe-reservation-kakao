package nextstep.reservation.repository.reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationJdbcTemplateRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Long add(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("name", reservation.getName())
                .addValue("date", Date.valueOf(reservation.getDate()))
                .addValue("time", Time.valueOf(reservation.getTime()))
                .addValue("theme_id", reservation.getTheme().getId());
        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM reservation JOIN theme ON reservation.theme_id = theme.id WHERE reservation.id = ?",
                reservationRowMapper,
                id
        ).stream().findAny();
    }


    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation JOIN theme ON reservation.theme_id = theme.id",
                reservationRowMapper
        );
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum)
            -> Reservation.builder()
            .id(rs.getLong("id"))
            .date(rs.getDate("date").toLocalDate())
            .time(rs.getTime("time").toLocalTime())
            .name(rs.getString("name"))
            .theme(Theme.builder()
                    .id(rs.getLong("theme.id"))
                    .name(rs.getString("theme.name"))
                    .desc(rs.getString("theme.desc"))
                    .price(rs.getInt("theme.price"))
                    .build())
            .build();

    @Override
    public boolean delete(final Long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id) == 1;
    }
}
