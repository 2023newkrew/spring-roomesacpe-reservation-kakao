package roomescape.service.reservation;

import roomescape.dto.Reservation;

public interface ReservationServiceInterface {
    Long create(Reservation reservation);
    Reservation find(Long idData);
}
