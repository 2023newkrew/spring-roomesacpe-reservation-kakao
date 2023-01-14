package roomescape.service.reservation;

import roomescape.dto.Reservation;

public interface ReservationServiceInterface {
    Long create(Reservation reservation);
    Reservation find(long id);
    void remove(long id);
}
