package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.request.ReservationRequestDTO;
import roomescape.reservation.dto.response.ReservationResponseDTO;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.DuplicatedReservationException;
import roomescape.reservation.exception.NoSuchReservationException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.theme.entity.Theme;
import roomescape.theme.exception.NoSuchThemeException;
import roomescape.theme.repository.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ThemeRepository themeRepository;

    public Long save(final ReservationRequestDTO reservationRequestDTO) {
        final Theme theme = this.themeRepository.findByName(reservationRequestDTO.getThemeName())
                .orElseThrow(NoSuchThemeException::new);

        final Reservation reservation = reservationRequestDTO.toEntity(theme.getId());

        this.reservationRepository.findByDateTimeAndThemeId(reservation.getDate(), reservation.getTime(),
                        reservation.getThemeId())
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
