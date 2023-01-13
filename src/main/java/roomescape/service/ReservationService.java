package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequestDTO;
import roomescape.dto.response.ReservationResponseDTO;
import roomescape.exception.DuplicatedReservationException;
import roomescape.exception.NoSuchReservationException;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Long save(final ReservationRequestDTO reservationRequestDTO) {
        final Reservation reservation = reservationRequestDTO.toEntity(2L);

        this.reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getTime())
                .ifPresent((e) -> {
                    throw new DuplicatedReservationException();
                });

        return this.reservationRepository.save(reservation);
    }

    public ReservationResponseDTO findById(final Long id) {
        Reservation reservation = this.reservationRepository.findById(id)
                .orElseThrow(NoSuchReservationException::new);

        return ReservationResponseDTO.from(reservation);
    }

    public void deleteById(Long id) {
        boolean deleted = this.reservationRepository.deleteById(id);

        if (!deleted) {
            throw new NoSuchReservationException();
        }
    }
}
