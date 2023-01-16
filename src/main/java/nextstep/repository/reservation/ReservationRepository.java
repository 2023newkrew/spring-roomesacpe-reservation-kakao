package nextstep.repository.reservation;

import nextstep.domain.reservation.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public interface ReservationRepository {
    Optional<Reservation> findById(long id);

    long add(Reservation reservation);

    int delete(long id);

    int countByDateAndTime(Long themeId, Date date, Time time);

}
