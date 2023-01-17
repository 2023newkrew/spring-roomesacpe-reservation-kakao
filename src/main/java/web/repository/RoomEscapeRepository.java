package web.repository;

import web.entity.Reservation;
import web.entity.Theme;

import java.util.List;
import java.util.Optional;

public interface RoomEscapeRepository {
    Long saveReservation(Reservation reservation);

    Optional<Reservation> findReservationById(long reservationId);

    Long deleteReservationById(long reservationId);

    void clearAllReservation();

    Long createTheme(Theme theme);

    Long deleteThemeById(long themeId);

    Optional<List<Theme>> getThemes();

    Optional<Theme> findThemeById(long themeId);
}
