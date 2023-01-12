package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationDTO;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

@Repository
public class ReservationJdbcRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation create(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            return ps;
        }, keyHolder);
        reservation.setId(keyHolder.getKey().longValue());
        return reservation;
    }

    @Override
    public Reservation find(long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean duplicate(ReservationDTO reservationDTO) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, reservationDTO.getDate(), reservationDTO.getTime()) > 0;
    }

    private RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Theme theme = new Theme(
                rs.getString("theme_name"),
                rs.getString("theme_desc"),
                rs.getInt("theme_price")
        );
        return new Reservation(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime(),
                rs.getString("name"),
                theme
        );
    };
}
