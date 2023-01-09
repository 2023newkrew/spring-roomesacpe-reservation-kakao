package nextstep.service;

import nextstep.dto.ReservationRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Override
    public Integer createReservation(ReservationRequestDTO reservationRequestDTO) {

        return 1;
    }
}
