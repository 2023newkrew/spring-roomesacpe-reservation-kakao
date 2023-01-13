package nextstep.reservation.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.entity.Theme;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservation);
        return jdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jdbcTemplate.query(
                "SELECT * FROM reservation WHERE id = ?",
                reservationRowMapper,
                id
        ).stream().findAny();
    }


    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation",
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
                    .name(rs.getString("theme_name"))
                    .desc(rs.getString("theme_desc"))
                    .price(rs.getInt("theme_price"))
                    .build())
            .build();

    @Override
    public boolean delete(final Long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id) == 1;
    }
}
