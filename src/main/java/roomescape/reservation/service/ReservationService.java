package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationRequestDTO;
import roomescape.reservation.dto.response.ReservationResponseDTO;
import roomescape.reservation.exception.DuplicatedReservationException;
import roomescape.reservation.exception.NoSuchReservationException;
import roomescape.reservation.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Long save(final ReservationRequestDTO reservationRequestDTO) {
        final Reservation reservation = reservationRequestDTO.toEntity(2L);

        this.reservationRepository.findByDateTimeAndThemeId(reservation.getDate(), reservation.getTime(),
                        reservation.getId())
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
