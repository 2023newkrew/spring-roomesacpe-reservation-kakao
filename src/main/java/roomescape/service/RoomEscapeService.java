package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exceptions.exception.DuplicatedReservationException;
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
        Reservation reservation = reservationRequestDto.toEntity();
        validateNotExistentReservation(reservation);
        return reservationRepository.save(reservationRequestDto.toEntity());
    }

    private void validateNotExistentReservation(Reservation reservation) {
        if (reservationRepository.isReservationIdDuplicated(reservation)) {
            throw new DuplicatedReservationException();
        }
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
