package nextstep.service;

import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

    Long createReservation(ReservationRequestDTO reservationRequestDTO);

    ReservationResponseDTO findReservation(Long id);

    void deleteById(Long id);
}
