package web.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.entity.Reservation;
import web.exception.ErrorCode;
import web.reservation.dto.ReservationRequestDto;
import web.reservation.dto.ReservationResponseDto;
import web.reservation.exception.ReservationException;
import web.reservation.repository.ReservationRepository;
import web.theme.exception.ThemeException;
import web.theme.repository.ThemeRepository;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public long reservation(ReservationRequestDto requestDto) {
        if (isNotExistTheme(requestDto.getThemeId())) {
            throw new ThemeException(ErrorCode.THEME_NOT_FOUND);
        }
        return reservationRepository.save(requestDto.toEntity());
    }

    private boolean isNotExistTheme(long themeId) {
        return themeRepository.findById(themeId)
                .isEmpty();
    }

    public ReservationResponseDto findReservationById(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ErrorCode.RESERVATION_NOT_FOUND));
        return ReservationResponseDto.of(reservationId, reservation);
    }

    public void cancelReservation(long reservationId) {
        long deleteReservationCount = reservationRepository.delete(reservationId);
        if (deleteReservationCount == 0) {
            throw new ReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }
}
