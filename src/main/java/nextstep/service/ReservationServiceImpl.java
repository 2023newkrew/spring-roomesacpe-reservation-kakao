package nextstep.service;

import java.sql.SQLException;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Long createReservation(ReservationRequestDTO reservationRequestDTO) throws SQLException {

        return reservationRepository.save(reservationRequestDTO);
    }

    @Override
    public ReservationResponseDTO findReservation(Long id) throws SQLException {

        return reservationRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        reservationRepository.deleteById(id);
    }
}
