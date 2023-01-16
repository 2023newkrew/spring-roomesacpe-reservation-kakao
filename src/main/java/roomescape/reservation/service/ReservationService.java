package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.common.exception.ErrorCode;
import roomescape.reservation.dto.request.ReservationRequestDTO;
import roomescape.reservation.dto.response.ReservationResponseDTO;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.ReservationException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.theme.entity.Theme;
import roomescape.theme.exception.ThemeException;
import roomescape.theme.repository.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ThemeRepository themeRepository;

    public Long save(final ReservationRequestDTO reservationRequestDTO) {
        final Theme theme = this.themeRepository.findByName(reservationRequestDTO.getThemeName())
                .orElseThrow(() -> new ThemeException(ErrorCode.INVALID_RESERVATION_THEME));

        final Reservation reservation = reservationRequestDTO.toEntity(theme.getId());

        this.reservationRepository.findByDateTimeAndThemeId(reservation.getDate(), reservation.getTime(),
                        reservation.getThemeId())
                .ifPresent((e) -> {
                    throw new ReservationException(ErrorCode.DUPLICATED_RESERVATION);
                });

        return this.reservationRepository.save(reservation);
    }

    public ReservationResponseDTO findById(final Long id) {
        Reservation reservation = this.reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException(ErrorCode.NO_SUCH_RESERVATION));

        return ReservationResponseDTO.from(reservation);
    }

    public void deleteById(Long id) {
        boolean deleted = this.reservationRepository.deleteById(id);

        if (!deleted) {
            throw new ReservationException(ErrorCode.NO_SUCH_RESERVATION);
        }
    }
}
