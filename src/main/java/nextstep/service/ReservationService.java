package nextstep.service;

import nextstep.domain.Reservation;

public interface ReservationService {
    Reservation reserve(Reservation reservation);

    Reservation findReservation(Long id);

    boolean cancelReservation(Long id);
}
