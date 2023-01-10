package nextstep.web;

import nextstep.Reservation;
import nextstep.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationQueryingDAO {
    private JdbcTemplate jdbcTemplate;

    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation findReservationById(Long id) {
        String sql = "select * from reservation where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime(),
                            resultSet.getString("name"),
                            new Theme(
                                    resultSet.getString("theme_name"),
                                    resultSet.getString("theme_desc"),
                                    resultSet.getInt("theme_price")
                            )
                    );
                    return reservation;
                }, id);
    }
}
