package reservation.respository;

import reservation.model.domain.Reservation;

public interface ReservationRepository {

    Long save(Reservation reservation);

    Reservation findById(Long id);

    int deleteById(Long id);
}

