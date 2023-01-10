package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.dto.ReservationRequestDto;
import nextstep.reservation.dto.ReservationResponseDto;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Long addReservation(final ReservationRequestDto requestDto) {
        return reservationRepository.add(new Reservation(requestDto));
    }

    public ReservationResponseDto getReservation(final Long id) {
        return new ReservationResponseDto(reservationRepository.getReservation(id));
    }

    public void deleteReservation(final Long id) {
        reservationRepository.delete(id);
    }
}
