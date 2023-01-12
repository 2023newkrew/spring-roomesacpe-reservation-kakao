package nextstep.reservations.domain.service.reservation;

import lombok.RequiredArgsConstructor;
import nextstep.reservations.domain.repository.reservation.ReservationRepository;
import nextstep.reservations.dto.reservation.ReservationRequestDto;
import nextstep.reservations.dto.reservation.ReservationResponseDto;
import nextstep.reservations.util.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    @Qualifier("webReservationRepository")
    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public Long addReservation(final ReservationRequestDto requestDto) {
        return reservationRepository.add(reservationMapper.RequestDtoToReservation(requestDto));
    }

    public ReservationResponseDto getReservation(final Long id) {
        return reservationMapper.ReservationToResponseDto(reservationRepository.findById(id));
    }

    public void deleteReservation(final Long id) {
        reservationRepository.remove(id);
    }
}
