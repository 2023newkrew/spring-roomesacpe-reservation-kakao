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

    public void addReservation(final ReservationRequestDto requestDto) {
        long id = reservationRepository.getLastId() + 1;
        reservationRepository.add(id, new Reservation(id, requestDto));
    }

    public ReservationResponseDto getReservation(final Long id) {
        return new ReservationResponseDto(reservationRepository.getReservation(id));
    }
}
