package nextstep.service;

import java.sql.SQLException;
import java.util.Optional;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.exception.ConflictException;
import nextstep.exception.NotFoundException;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

        validate(reservationRequestDTO);

        return reservationRepository.save(reservationRequestDTO);
    }

    private void validate(ReservationRequestDTO reservationRequestDTO) throws SQLException {
        boolean isExist = reservationRepository.existByDateAndTime(
                reservationRequestDTO.getDate(),
                reservationRequestDTO.getTime());
        if (isExist) {
            throw new ConflictException("날짜와 시간이 중복되는 예약은 생성할 수 없습니다.");
        }
    }

    @Override
    public ReservationResponseDTO findReservation(Long id) throws SQLException {
        try {
            Optional<ReservationResponseDTO> reservationRequestDTO = reservationRepository.findById(id);
            return reservationRequestDTO.orElse(null);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        if (reservationRepository.deleteById(id) == 0) {
            throw new NotFoundException("해당 예약이 존재하지 않습니다.");
        }
    }
}
