package roomescape.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.model.Reservation;
import roomescape.model.Theme;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.ReservationRepository;

import java.time.LocalDateTime;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ThemeService themeService;

    public ReservationService(ReservationJdbcRepository reservationRepository, ThemeService themeService) {
        this.reservationRepository = reservationRepository;
        this.themeService = themeService;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto req) {
        LocalDateTime dateTime = LocalDateTime.of(req.getDate(), req.getTime());
        Reservation reservation = new Reservation(null, dateTime, req.getName(), req.getThemeId());
        try {
            reservation.setId(reservationRepository.save(reservation));
        } catch (DuplicateKeyException e) {
            throw new RoomEscapeException(ErrorCode.RESERVATION_DATETIME_ALREADY_EXISTS);
        }
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return new ReservationResponseDto(reservation, theme);
    }

    public ReservationResponseDto findReservation(Long id) {
        Reservation reservation = getReservation(id);
        Theme theme = themeService.getTheme(reservation.getThemeId());
        return new ReservationResponseDto(reservation, theme);
    }

    public void cancelReservation(Long id) {
        Boolean isCanceled = reservationRepository.delete(id);
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
