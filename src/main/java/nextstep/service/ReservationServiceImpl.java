package nextstep.service;

import nextstep.dto.ReservationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationService reservationService;

    @Autowired
    public ReservationServiceImpl(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public Integer createReservation(ReservationRequestDTO reservationRequestDTO) {

        return reservationService.createReservation(reservationRequestDTO);
    }
}
