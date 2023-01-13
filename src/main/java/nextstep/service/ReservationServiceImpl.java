package nextstep.service;

import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import nextstep.dto.ReservationRequestDTO;
import nextstep.dto.ReservationResponseDTO;
import nextstep.entity.Reservation;
import nextstep.exception.ConflictException;
import nextstep.exception.NotFoundException;
import nextstep.mapstruct.ReservationMapper;
import nextstep.repository.ReservationRepository;

@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public Long createReservation(ReservationRequestDTO reservationRequestDTO) throws SQLException {
        validate(reservationRequestDTO);
        return reservationRepository.save(reservationRequestDTO);
    }

    private void validate(ReservationRequestDTO reservationRequestDTO) throws SQLException {
        boolean isExist = reservationRepository.existByDateAndTimeAndThemeId(reservationRequestDTO.getDate(),
                reservationRequestDTO.getTime(), reservationRequestDTO.getThemeId());
        if (isExist) {
            throw new ConflictException("날짜와 시간이 중복되는 예약은 생성할 수 없습니다.");
        }
    }

    @Override
    public ReservationResponseDTO findReservation(Long id) throws SQLException {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation != null) {
            return ReservationMapper.INSTANCE.reservationToResponseDTO(reservation);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        if (reservationRepository.deleteById(id) == 0) {
            throw new NotFoundException("해당 예약이 존재하지 않습니다.");
        }
    }
}
