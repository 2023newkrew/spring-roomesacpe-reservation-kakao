package nextstep.dao;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationDAO {
    String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
    String FIND_BY_ID_SQL = "SELECT * FROM reservation WHERE id = ?";
    String FIND_BY_DATE_TIME_SQL = "SELECT * FROM reservation WHERE date = ? AND time = ?";
    String DELETE_BY_ID_SQL = "DELETE FROM reservation WHERE id = ?";

    RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
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

    Long save(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findByDateAndTime(LocalDate date, LocalTime time);

    int deleteById(Long id);

    static PreparedStatementCreator getInsertPreparedStatementCreator(Reservation reservation) {
        return connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            return ps;
        };
    }
}
