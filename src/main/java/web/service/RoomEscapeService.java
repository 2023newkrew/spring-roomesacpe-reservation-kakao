package web.service;

import org.springframework.stereotype.Service;
import web.dto.ReservationRequestDto;
import web.dto.ReservationResponseDto;
import web.dto.ThemeRequestDto;
import web.dto.ThemeResponseDto;
import web.entity.Reservation;
import web.entity.Theme;
import web.exception.ReservationNotFoundException;
import web.exception.ThemeNotFoundException;
import web.repository.ReservationRepository;
import web.repository.ThemeRepository;


@Service
public class RoomEscapeService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public RoomEscapeService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public long reservation(ReservationRequestDto requestDto) {
        return reservationRepository.save(requestDto.toEntity());
    }

    public ReservationResponseDto findReservationById(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        return ReservationResponseDto.of(reservationId, reservation);
    }

    public void cancelReservation(long reservationId) {
        long deleteReservationCount = reservationRepository.delete(reservationId);
        if (deleteReservationCount == 0) {
            throw new ReservationNotFoundException(reservationId);
        }
    }

    public Long createTheme(ThemeRequestDto requestDto) {
        return themeRepository.createTheme(requestDto.toEntity());
    }

    public ThemeResponseDto getThemes() {
        Theme theme = themeRepository.getThemes()
                .orElseThrow(() -> new ThemeNotFoundException());
        return ThemeResponseDto.of(1, theme);
    }

    public void deleteTheme(long themeId) {
        long deleteThemeCount = themeRepository.deleteTheme(themeId);
        if (deleteThemeCount == 0) {
            throw new ThemeNotFoundException();
        }
    }
}
