package web.service;

import org.springframework.stereotype.Service;
import web.dto.ReservationRequestDto;
import web.dto.ReservationResponseDto;
import web.entity.Reservation;
import web.exception.ReservationNotFoundException;
import web.repository.ReservationRepository;


@Service
public class RoomEscapeService {

    private final ReservationRepository reservationRepository;

    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public long reservation(ReservationRequestDto requestDto) {
        return reservationRepository.save(requestDto.toEntity());
    }

    public ReservationResponseDto findReservationById(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        return ReservationResponseDto.of(reservationId, reservation);
    }
}
