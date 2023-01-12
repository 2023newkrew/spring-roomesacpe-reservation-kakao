package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.dto.ReservationRequestDto;
import web.dto.ReservationResponseDto;
import web.entity.Reservation;
import web.exception.ReservationNotFoundException;
import web.repository.ReservationRepository;

import static web.exception.ErrorCode.RESERVATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoomEscapeService {

    private final ReservationRepository reservationRepository;

    public long reservation(ReservationRequestDto requestDto) {
        return reservationRepository.save(requestDto.toEntity());
    }

    public ReservationResponseDto findReservationById(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND));
        return ReservationResponseDto.of(reservationId, reservation);
    }

    public void cancelReservation(long reservationId) {
        long deleteReservationCount = reservationRepository.delete(reservationId);
        if (deleteReservationCount == 0) {
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND);
        }
    }
}