package nextstep.reservation.repository.reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
        String sql = "SELECT * FROM reservation" +
                     "JOIN theme ON reservation.theme_id = theme.id" +
                     "WHERE reservation.id = ?";
        return jdbcTemplate.query(sql, reservationRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation" +
                     "JOIN theme ON reservation.theme_id = theme.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public boolean delete(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id) == 1;
    }

    @Override
    public Optional<Reservation> getReservationByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT * FROM reservation" +
                     "JOIN theme ON reservation.theme_id = theme.id" +
                     "WHERE reservation.date = (?) and time = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, date, time)
                .stream()
                .findAny();
    }

    @Override
    public Optional<Reservation> getReservationByName(String name) {
        String sql = "SELECT * FROM reservation" +
                     "JOIN theme ON reservation.theme_id = theme.id" +
                     "WHERE reservation.name = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, name)
                .stream()
                .findAny();
    }
}
