package nextstep.repository;

import nextstep.domain.reservation.Reservation;

import java.sql.Date;
import java.sql.Time;

public interface ReservationRepo {
    public Reservation findById(long id);

    public long add(Reservation reservation);

    public int delete(long id);

    public int countWhenDateAndTimeMatch(Date date, Time time);

    public int reset();

}
