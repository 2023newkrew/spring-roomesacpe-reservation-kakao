package web.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.entity.Reservation;
import web.reservation.dto.ReservationRequestDto;
import web.reservation.dto.ReservationResponseDto;
import web.reservation.exception.ReservationException;
import web.reservation.repository.ReservationRepository;

import static web.reservation.exception.ErrorCode.RESERVATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public long reservation(ReservationRequestDto requestDto) {
        return reservationRepository.save(requestDto.toEntity());
    }

    public ReservationResponseDto findReservationById(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
        return ReservationResponseDto.of(reservationId, reservation);
    }

    public void cancelReservation(long reservationId) {
        long deleteReservationCount = reservationRepository.delete(reservationId);
        if (deleteReservationCount == 0) {
            throw new ReservationException(RESERVATION_NOT_FOUND);
        }
    }
}
