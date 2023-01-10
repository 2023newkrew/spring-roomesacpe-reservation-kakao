package nextstep.repository;

import nextstep.dto.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationMemoryRepository implements ReservationRepository{
    @Override
    public void createReservation(Reservation reservation) {

    }

    @Override
    public Reservation findReservation(long id) {
        return null;
    }

    @Override
    public void deleteReservation(long id) {

    }
}
