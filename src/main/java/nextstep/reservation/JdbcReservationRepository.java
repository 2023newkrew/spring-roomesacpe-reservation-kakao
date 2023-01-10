package nextstep.reservation;

import nextstep.reservation.exception.CreateReservationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
@Primary
public class JdbcReservationRepository implements ReservationRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation create(Reservation reservation) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation").usingGeneratedKeyColumns("id");
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        if (findByDateTime(reservation.getDate(), reservation.getTime())){
            throw new CreateReservationException();
        }
        Long id = insertActor.executeAndReturnKey(parameters).longValue();
        return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName() ,reservation.getTheme());
    }

    @Override
    public Reservation findById(long id) {
        String sql = "select * from reservation where id= ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime(),
                            resultSet.getString("name"),
                            new Theme(resultSet.getString("theme_name"),
                                    resultSet.getString("theme_desc"),
                                    resultSet.getInt("theme_price"))
                    );
                    return reservation;
                }, id);
    }

    @Override
    public Boolean findByDateTime(LocalDate date, LocalTime time) {
        String sql = "select exists (select * from reservation where date= ? and time = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, date, time);
    }

    @Override
    public Boolean delete(long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id)) == 1 ;
    }

    @Override
    public void clear() {

    }

}
