package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.repository.ReservationRepository;
import roomescape.exceptions.exception.NoSuchReservationException;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
@Service
public class RoomEscapeService {
    private final ReservationRepository reservationRepository;

    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long makeReservation(ReservationRequestDto reservationRequestDto) {
        return reservationRepository.save(reservationRequestDto.toEntity());
    }

    public ReservationResponseDto findReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchReservationException());
        return ReservationResponseDto.of(reservationId, reservation);
    }

    public void cancelReservation(Long reservationId) {
        int deleteReservationCount = reservationRepository.delete(reservationId);
        if (deleteReservationCount == 0) {
            throw new NoSuchReservationException();
        }
    }
}
