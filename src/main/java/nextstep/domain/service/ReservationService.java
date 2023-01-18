package nextstep.domain.service;

import nextstep.domain.Reservation;
import nextstep.domain.dto.ReservationRequest;
import nextstep.domain.dto.ReservationResponse;

public interface ReservationService {
    Reservation newReservation(ReservationRequest reservationRequest);

    ReservationResponse findReservation(long id);

    boolean deleteReservation(long id);

}
