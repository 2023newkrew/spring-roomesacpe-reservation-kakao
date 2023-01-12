package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public interface ReservationRepository {

    public Long addReservation(Reservation reservation);
    public int checkSchedule(ReservationRequest reservationRequest);
    public Reservation findReservation(Long id);
    public int removeReservation(Long id);

}
