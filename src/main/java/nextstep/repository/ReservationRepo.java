package nextstep.repository;

import nextstep.domain.reservation.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public interface ReservationRepo {
    Optional<Reservation> findById(long id);

    long add(Reservation reservation);

    int delete(long id);

    int countByDateAndTime(Date date, Time time);

}
