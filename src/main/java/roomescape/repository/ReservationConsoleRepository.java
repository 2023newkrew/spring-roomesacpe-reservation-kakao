package roomescape.repository;

import roomescape.domain.Reservation;

import java.sql.*;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Theme;

public class ReservationConsoleRepository extends BaseConsoleRepository implements ReservationRepository {

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = (rs, rowNum) -> {
        Integer count = rs.getInt(1);
        return count;
    };

    private static final RowMapper<Optional<Reservation>> ROW_MAPPER = (rs, rowNum) -> {
        Optional<Reservation> reservation = Optional.of(new Reservation(
                    rs.getLong("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime(),
                    rs.getString("name"),
                    new Theme(
                            rs.getLong("tid"),
                            rs.getString("theme_name"),
                            rs.getString("theme_desc"),
                            rs.getInt("theme_price")
                    )
        ));
        return reservation;
    };


    @Override
    public Long addReservation(Reservation reservation) {
        final String SQL = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?);";
        return insert(SQL,
                Date.valueOf(reservation.getDate()),
                Time.valueOf(reservation.getTime()),
                reservation.getName(),
                reservation.getTheme().getName(),
                reservation.getTheme().getDesc(),
                reservation.getTheme().getPrice()
        );
    }

    @Override
    public int checkSchedule(String date, String time) {
        final String SQL = "SELECT COUNT(*) FROM reservation WHERE `date` = ? AND `time` = ?;";
        int count = query(SQL, COUNT_ROW_MAPPER, Date.valueOf(date), Time.valueOf(time)).get(0);
        return count;
    }

    @Override
    public Optional<Reservation> findReservation(Long id) {
        final String SQL = "SELECT r.*, t.id AS tid FROM reservation r, theme t WHERE r.id = ? AND r.theme_name = t.name;";
        Optional<Reservation> reservation = query(SQL, ROW_MAPPER, id).get(0);
        return reservation;
    }

    @Override
    public int removeReservation(Long id) {
        final String SQL = "DELETE FROM reservation WHERE id = ?;";
        return delete(SQL, id);
    }

}