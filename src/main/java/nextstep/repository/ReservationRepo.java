package nextstep.repository;

import nextstep.domain.reservation.Reservation;

import java.sql.Date;
import java.sql.Time;

public interface ReservationRepo {
    public Reservation findById(long id);

    public long save(Reservation reservation);

    public int delete(long id);

    public int findByDateAndTime(Date date, Time time);

}
