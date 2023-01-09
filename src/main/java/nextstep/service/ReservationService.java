package nextstep.service;

import nextstep.dto.ReservationRequestDTO;

public interface ReservationService {

    Integer createReservation(ReservationRequestDTO reservationRequestDTO);
}
