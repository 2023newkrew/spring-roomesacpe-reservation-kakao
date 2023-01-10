package nextstep.repository;

import nextstep.domain.reservation.Reservation;

import java.sql.Date;
import java.sql.Time;

public interface ReservationRepo {
    Reservation findById(long id);

    long add(Reservation reservation);

    int delete(long id);

    int countByDateAndTime(Date date, Time time);

}
