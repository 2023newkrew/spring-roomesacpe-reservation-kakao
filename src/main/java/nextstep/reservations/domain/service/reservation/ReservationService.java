package nextstep.reservations.domain.service.reservation;

import nextstep.reservations.domain.repository.reservation.ReservationRepository;
import nextstep.reservations.dto.reservation.ReservationRequestDto;
import nextstep.reservations.dto.reservation.ReservationResponseDto;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.util.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(final ReservationRepository reservationRepository, final ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public Long addReservation(final ReservationRequestDto requestDto) {
        return reservationRepository.add(reservationMapper.requestDtoToReservation(requestDto));
    }

    public ReservationResponseDto getReservation(final Long id) {
        return reservationMapper.reservationToResponseDto(reservationRepository.findById(id));
    }

    public void deleteReservation(final Long id) {
        int removeCount = reservationRepository.remove(id);

        if (removeCount == 0) throw new NoSuchReservationException();
    }
}
