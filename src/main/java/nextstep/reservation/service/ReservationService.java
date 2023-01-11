package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.reservation.dto.ReservationResponseDto;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.reservation.util.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public Long addReservation(final ReservationRequestDto requestDto) {
        return reservationRepository.add(reservationMapper.RequestDtoToReservation(requestDto));
    }

    public ReservationResponseDto getReservation(final Long id) {
        return reservationMapper.ReservationToResponseDto(reservationRepository.getReservation(id));
    }

    public void deleteReservation(final Long id) {
        reservationRepository.delete(id);
    }
}
