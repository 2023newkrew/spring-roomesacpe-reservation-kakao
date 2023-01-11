package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface ReservationDao {
    default RowMapper<Reservation> getRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"),
                new Theme(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price"))
        );
    }

    default PreparedStatementCreator getPreparedStatementCreatorForSave(Reservation reservation) {
        return (connection) -> {
            final String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setDate(1, Date.valueOf(reservation.getDate()));
            ps.setTime(2, Time.valueOf(reservation.getTime()));
            ps.setString(3, reservation.getName());
            ps.setString(4, reservation.getTheme().getName());
            ps.setString(5, reservation.getTheme().getDesc());
            ps.setInt(6, reservation.getTheme().getPrice());
            return ps;
        };
    }

    Long save(Reservation reservation);

    int countByDateAndTime(LocalDate date, LocalTime time);

    Optional<Reservation> findById(Long id);

    void delete(Long id);
}
