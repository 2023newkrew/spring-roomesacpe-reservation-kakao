package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.entity.Theme;
import roomescape.exceptions.exception.DuplicatedReservationException;
import roomescape.exceptions.exception.ThemeNotFoundException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.exceptions.exception.ReservationNotFoundException;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.theme.repository.ThemeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
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
        Theme theme = themeRepository.findById(reservation.getThemeId())
                .orElseThrow(ThemeNotFoundException::new);
        return ReservationResponseDto.of(reservation, theme);
    }

    public void cancelReservation(Long reservationId) {
        int deleteReservationCount = reservationRepository.delete(reservationId);
        if (deleteReservationCount == 0) {
            throw new ReservationNotFoundException();
        }
    }
}
