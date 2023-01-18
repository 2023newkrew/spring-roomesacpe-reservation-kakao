package nextstep.roomescape.repository;

import nextstep.roomescape.repository.model.Reservation;
import nextstep.roomescape.repository.model.Theme;
import nextstep.roomescape.exception.DuplicateEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@Primary
public class ReservationRepositoryJdbcImpl implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation.id"),
            resultSet.getDate("reservation.date").toLocalDate(),
            resultSet.getTime("reservation.time").toLocalTime(),
            resultSet.getString("reservation.name"),
            new Theme(
                    resultSet.getLong("theme.id"),
                    resultSet.getString("theme.name"),
                    resultSet.getString("theme.desc"),
                    resultSet.getInt("theme.price")
            )

    );

    @Override
    public Long create(Reservation reservation) {
        Theme theme = reservation.getTheme();
        if (findByDateTime(reservation.getDate(), reservation.getTime())) {
            throw new DuplicateEntityException("예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있습니다.");
        }
        String sql = "insert into reservation (date, time, name, theme_id) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setLong(4, theme.getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Reservation findById(long id) {
        String sql = "SELECT reservation.id, reservation.date, reservation.time, reservation.name, theme.id, theme.name, theme.desc, theme.price " +
                "from reservation " +
                "inner join theme on reservation.theme_id = theme.id " +
                "where reservation.id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Boolean findByDateTime(LocalDate date, LocalTime time) {
        String sql = "select exists (select * from reservation where date= ? and time = ?)";
        try {
            return jdbcTemplate.queryForObject(sql, Boolean.class, date, time);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void delete(long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        String sql = "SELECT reservation.id, reservation.date, reservation.time, reservation.name, theme.id, theme.name, theme.desc, theme.price " +
                "from reservation " +
                "inner join theme on reservation.theme_id = theme.id " +
                "where theme.id = ?;";
        return jdbcTemplate.query(sql, rowMapper, themeId);
    }

}
