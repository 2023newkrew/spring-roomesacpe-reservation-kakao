package nextstep.repository;

import nextstep.domain.reservation.Reservation;

public interface ReservationRepo {
    public Reservation findById(long id);

    public long add(Reservation reservation);

    public int delete(long id);

}
