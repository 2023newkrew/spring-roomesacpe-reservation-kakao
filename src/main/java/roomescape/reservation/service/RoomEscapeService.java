package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.exceptions.exception.DuplicatedReservationException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.exceptions.exception.ReservationNotFoundException;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
@Service
public class RoomEscapeService {
    private final ReservationRepository reservationRepository;

    public RoomEscapeService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long makeReservation(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationRequestDto.toEntity();
        validateNonExistentReservation(reservation);
        return reservationRepository.save(reservation);
    }

    private void validateNonExistentReservation(Reservation reservation) {
        if (reservationRepository.isReservationDuplicated(reservation)) {
            throw new DuplicatedReservationException();
        }
    }

    public ReservationResponseDto findReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);
        return ReservationResponseDto.of(reservation);
    }

    public void cancelReservation(Long reservationId) {
        int deleteReservationCount = reservationRepository.delete(reservationId);
        if (deleteReservationCount == 0) {
            throw new ReservationNotFoundException();
        }
    }
}
