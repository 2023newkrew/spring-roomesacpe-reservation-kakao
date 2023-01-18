package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dto.ReservationRequestDto;
import web.dto.ReservationResponseDto;
import web.dto.ThemeRequestDto;
import web.dto.ThemeResponseDto;
import web.entity.Reservation;
import web.entity.Theme;
import web.exception.ReservationNotFoundException;
import web.exception.ThemeDeleteNotAllowException;
import web.exception.ThemeNotFoundException;
import web.repository.RoomEscapeRepositoryImpl;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoomEscapeService {
    private static final LocalTime BEGIN_TIME = LocalTime.of(11, 0, 0);
    private static final LocalTime LAST_TIME = LocalTime.of(20, 30, 0);

    private final RoomEscapeRepositoryImpl roomEscapeRepositoryImpl;

    @Autowired
    public RoomEscapeService(RoomEscapeRepositoryImpl roomEscapeRepositoryImpl) {
        this.roomEscapeRepositoryImpl = roomEscapeRepositoryImpl;
    }

    public Long reservation(ReservationRequestDto requestDto) throws IllegalArgumentException {
        if (isInvalidTime(requestDto.getTime())) {
            throw new IllegalArgumentException("시간이 올바르지 않습니다.");
        }
        if (isInvalidThemeId(requestDto.getThemeId())) {
            throw new IllegalArgumentException("테마 번호가 올바르지 않습니다.");
        }
        return roomEscapeRepositoryImpl.saveReservation(requestDto.toEntity());
    }

    private boolean isInvalidThemeId(long themeId) {
        if (roomEscapeRepositoryImpl.findThemeById(themeId).isEmpty()) {
            return true;
        }
        return false;
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
        Reservation reservation = roomEscapeRepositoryImpl.findReservationById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
        Theme theme = roomEscapeRepositoryImpl.findThemeById(reservation.getThemeId())
                .orElseThrow(ThemeNotFoundException::new);
        return ReservationResponseDto.of(reservationId, reservation, theme);
    }

    public void cancelReservation(long reservationId) {
        long deleteReservationCount = roomEscapeRepositoryImpl.deleteReservationById(reservationId);
        if (deleteReservationCount == 0) {
            throw new ReservationNotFoundException(reservationId);
        }
    }

    public Long createTheme(ThemeRequestDto requestDto) {
        return roomEscapeRepositoryImpl.createTheme(requestDto.toEntity());
    }

    public List<ThemeResponseDto> getThemes() {
        List<Theme> themes = roomEscapeRepositoryImpl.getThemes()
                .orElseThrow(ThemeNotFoundException::new);
        return themes.stream().map(ThemeResponseDto::of).collect(Collectors.toList());
    }

    public void deleteTheme(long themeId) {
        if (roomEscapeRepositoryImpl.isExistReservationByThemeId(themeId)) {
            throw new ThemeDeleteNotAllowException();
        }
        long deleteThemeCount = roomEscapeRepositoryImpl.deleteThemeById(themeId);
        if (deleteThemeCount == 0) {
            throw new ThemeNotFoundException();
        }
    }
}
