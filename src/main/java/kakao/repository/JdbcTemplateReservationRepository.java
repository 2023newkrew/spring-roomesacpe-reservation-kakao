package kakao.repository;

import kakao.model.request.ReservationRequest;
import kakao.model.response.ReservationResponse;
import kakao.model.response.Theme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(ReservationRequest reservationRequest, Theme theme) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("date", Date.valueOf(reservationRequest.getDate()))
                .addValue("time", Time.valueOf(reservationRequest.getTime()))
                .addValue("name", reservationRequest.getName())
                .addValue("themeName", theme.getName())
                .addValue("themeDesc", theme.getDesc())
                .addValue("themePrice", theme.getPrice());

        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public Optional<ReservationResponse> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE id=?", reservationResponseRowMapper(), id)
                .stream()
                .findAny();
    }

    @Override
    public List<ReservationResponse> findByDateAndTime(LocalDate date, LocalTime time) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE date=? AND time=?", reservationResponseRowMapper(), date, time);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id=?", id);
    }

    private RowMapper<ReservationResponse> reservationResponseRowMapper() {
        return (resultSet, rowNumber) -> {
            Long id = resultSet.getLong("id");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime time = resultSet.getTime("time").toLocalTime();
            String name = resultSet.getString("name");

            String themeName = resultSet.getString("theme_name");
            String themeDesc = resultSet.getString("theme_desc");
            Integer themePrice = resultSet.getInt("theme_price");

            Theme theme = new Theme(themeName, themeDesc, themePrice);

            return new ReservationResponse(id, date, time, name, theme);
        };
    }
}