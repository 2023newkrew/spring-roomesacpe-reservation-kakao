package nextstep.dao;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationSaveForm;
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
    String INSERT_SQL = "INSERT INTO reservation (date, time, name, theme_id) VALUES (?, ?, ?, ?)";
    String FIND_ALL_SQL = "SELECT reservation.*, t.name AS theme_name, t.desc AS theme_desc, t.price AS theme_price"
            + " FROM reservation INNER JOIN theme AS t"
            + " ON reservation.theme_id = t.id";
    String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE reservation.id = ?";
    String FIND_BY_DATE_TIME_SQL = FIND_ALL_SQL + " WHERE date = ? AND time = ?";
    String DELETE_BY_ID_SQL = "DELETE FROM reservation WHERE id = ?";

    RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name"),
            new Theme(
                    resultSet.getLong("theme_id"),
                    resultSet.getString("theme_name"),
                    resultSet.getString("theme_desc"),
                    resultSet.getInt("theme_price")
            )
    );

    Long save(ReservationSaveForm reservationSaveForm);

    Reservation findById(Long id);

    List<Reservation> findByDateAndTime(LocalDate date, LocalTime time);

    int deleteById(Long id);

    static PreparedStatementCreator getInsertPreparedStatementCreator(ReservationSaveForm reservationSaveForm) {
        return connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservationSaveForm.getDate()));
            ps.setTime(2, Time.valueOf(reservationSaveForm.getTime()));
            ps.setString(3, reservationSaveForm.getName());
            ps.setLong(4, reservationSaveForm.getThemeId());
            return ps;
        };
    }
}
