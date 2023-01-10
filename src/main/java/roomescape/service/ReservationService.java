package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.repository.ReservationMemoryRepository;
import roomescape.repository.ReservationRepository;

import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationMemoryRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(ReservationRequestDto reservationRequest) {
        if (reservationRepository.hasOneByDateAndTime(reservationRequest.getDate(), reservationRequest.getTime())) {
            throw new IllegalArgumentException("Already have reservation at that date & time");
        }
        return reservationRepository.save(new Reservation(reservationRequest));
    }

    public ReservationResponseDto findReservation(Long reservationId) {
        Reservation reservation = reservationRepository
                .findOneById(reservationId)
                .orElseThrow(() -> {throw new NoSuchElementException("No Reservation by that Id");});
        return new ReservationResponseDto(reservation);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.delete(reservationId);
    }
}
