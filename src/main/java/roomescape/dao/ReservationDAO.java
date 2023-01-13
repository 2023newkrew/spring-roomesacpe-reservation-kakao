package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.jdbc.core.RowMapper;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

public abstract class ReservationDAO {

    private static final String ID_TABLE = "id";
    private static final String DATE_TABLE = "date";
    private static final String TIME_TABLE = "time";
    private static final String NAME_TABLE = "name";
    private static final String THEME_ID_TABLE = "theme_id";

    private static final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong(ID_TABLE),
            resultSet.getDate(DATE_TABLE).toLocalDate(),
            resultSet.getTime(TIME_TABLE).toLocalTime(),
            resultSet.getString(NAME_TABLE),
            resultSet.getLong(THEME_ID_TABLE));
    private static final RowMapper<Boolean> existRowMapper = (resultSet, rowNum) -> resultSet.getBoolean("result");

    protected void validateReservation(Reservation reservation) {
        if (!reservation.isValid()) {
            throw new BadRequestException();
        }
        if (existReservation(reservation.getDate(), reservation.getTime())) {
            throw new BadRequestException();
        }
    }

    protected static RowMapper<Reservation> getReservationRowMapper() {
        return reservationRowMapper;
    }

    protected static RowMapper<Boolean> getExistRowMapper() {
        return existRowMapper;
    }

    protected abstract boolean existReservation(LocalDate date, LocalTime time);
    public abstract Long addReservation(Reservation reservation);
    public abstract Reservation findReservation(Long id);
    public abstract void deleteReservation(Long id);
}
