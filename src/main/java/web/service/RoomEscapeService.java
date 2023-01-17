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

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoomEscapeService {
    private static final LocalTime BEGIN_TIME = LocalTime.of(11, 0, 0);
    private static final LocalTime LAST_TIME = LocalTime.of(20, 30, 0);

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;


    public RoomEscapeService(ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    public Long reservation(ReservationRequestDto requestDto) throws IllegalArgumentException {
        if (isInvalidTime(requestDto.getTime())) {
            throw new IllegalArgumentException();
        }
        return reservationRepository.save(requestDto.toEntity());
    }

    private boolean isInvalidTime(LocalTime reservationTime) {
        if (isOutOfBusinessHours(reservationTime)) {
            return true;
        }
        if (isUnitOf30Minutes(reservationTime)) {
            return true;
        }
        return false;
    }

    private boolean isOutOfBusinessHours(LocalTime time) {
        return time.isBefore(BEGIN_TIME) || time.isAfter(LAST_TIME);
    }

    private boolean isUnitOf30Minutes(LocalTime time) {
        return time.getMinute() % 30 != 0;
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

    public List<ThemeResponseDto> getThemes() {
        List<Theme> themes = themeRepository.getThemes()
                .orElseThrow(ThemeNotFoundException::new);
        return themes.stream().map(ThemeResponseDto::of).collect(Collectors.toList());
    }

    public void deleteTheme(long themeId) {
        long deleteThemeCount = themeRepository.deleteTheme(themeId);
        if (deleteThemeCount == 0) {
            throw new ThemeNotFoundException();
        }
    }
}
