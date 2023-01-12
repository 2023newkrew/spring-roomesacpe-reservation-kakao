package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.model.Reservation;
import roomescape.model.Theme;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeService themeService;

    public ReservationService(ReservationJdbcRepository reservationRepository, ThemeService themeService) {
        this.reservationRepository = reservationRepository;
        this.themeService = themeService;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto req) {
        if (reservationRepository.has(req.getDate(), req.getTime())) {
            throw new RoomEscapeException(ErrorCode.RESERVATION_DATETIME_ALREADY_EXISTS);
        }
        Reservation reservation = new Reservation(req);
        Long id = reservationRepository.save(reservation);
        reservation.setId(id);
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return new ReservationResponseDto(reservation, theme);
    }

    public ReservationResponseDto findReservation(Long id) {
        Reservation reservation = getReservation(id);
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return new ReservationResponseDto(reservation, theme);
    }

    public void cancelReservation(Long id) {
        Boolean isCanceled = reservationRepository.delete(id) == 1;
        if (!isCanceled) {
            throw new RoomEscapeException(ErrorCode.NO_SUCH_ELEMENT);
        }
    }

    Reservation getReservation(Long id) {
        return reservationRepository.find(id).orElseThrow(() -> {
            throw new RoomEscapeException(ErrorCode.NO_SUCH_ELEMENT);
        });
    }
}
