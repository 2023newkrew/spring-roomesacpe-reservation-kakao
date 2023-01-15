package nextstep.reservation.dao;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCTemplateThemeReservationDao implements ThemeReservationDao {

    private static final String INSERT_SQL = "INSERT INTO RESERVATION(`DATE`, `TIME`, `NAME`, `THEME_ID`) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_RESERVATION_ID_SQL = "DELETE FROM RESERVATION WHERE ID = ?";
    private static final String SELECT_BY_RESERVATION_ID_SQL = "SELECT `ID`, `DATE`, `TIME`, `NAME`, `THEME_ID`  FROM RESERVATION WHERE `ID` = ?";
    private static final String SELECT_BY_DATE_TIME_SQL = "SELECT * FROM `RESERVATION` WHERE `date` = ? AND `time` = ?";

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->{
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                resultSet.getLong("theme_id"));
    };

    @Override
    public int insert(Reservation reservation){
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int insertCount = jdbcTemplate.update((Connection con) -> {
                PreparedStatement psmt = con.prepareStatement(INSERT_SQL, new String[] {"id"});
                int parameterIndex = 1;
                psmt.setDate(parameterIndex++, java.sql.Date.valueOf(reservation.getDate()));
                psmt.setTime(parameterIndex++, java.sql.Time.valueOf(reservation.getTime()));
                psmt.setString(parameterIndex++, reservation.getName());
                psmt.setLong(parameterIndex++, reservation.getThemeId());
                return psmt;
            }, keyHolder);
        reservation.setId(keyHolder.getKey().longValue());

        return insertCount;
    }

    @Override
    public int delete(Long id){
        return jdbcTemplate.update(DELETE_BY_RESERVATION_ID_SQL, id);
    }

    @Override
    public Optional<Reservation> findById(Long id){
        return jdbcTemplate.query(SELECT_BY_RESERVATION_ID_SQL, reservationRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Reservation> findByDatetime(LocalDate date, LocalTime time) {
        return jdbcTemplate.query(SELECT_BY_DATE_TIME_SQL, reservationRowMapper, date, time)
                .stream()
                .findFirst();
    }
}
