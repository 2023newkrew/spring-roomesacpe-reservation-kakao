package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    Long save(Reservation reservation);

    int countByDateAndTime(LocalDate date, LocalTime time);

    Optional<Reservation> findById(Long id);

    void delete(Long id);
}
