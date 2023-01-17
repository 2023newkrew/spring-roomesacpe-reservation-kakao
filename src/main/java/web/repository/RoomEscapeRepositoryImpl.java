package web.repository;

import org.springframework.stereotype.Repository;
import web.entity.Reservation;
import web.entity.Theme;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomEscapeRepositoryImpl implements RoomEscapeRepository {
    private ReservationDaoImpl reservationDaoImpl;
    private ThemeDaoImpl themeDaoImpl;

    public RoomEscapeRepositoryImpl(ReservationDaoImpl reservationDaoImpl, ThemeDaoImpl themeDaoImpl) {
        this.reservationDaoImpl = reservationDaoImpl;
        this.themeDaoImpl = themeDaoImpl;
    }

    @Override
    public Long saveReservation(Reservation reservation) {
        return reservationDaoImpl.save(reservation);
    }

    @Override
    public Optional<Reservation> findReservationById(long reservationId) {
        return reservationDaoImpl.findById(reservationId);
    }

    @Override
    public Long deleteReservationById(long reservationId) {
        return reservationDaoImpl.delete(reservationId);
    }

    @Override
    public void clearAllReservation() {
        reservationDaoImpl.clearAll();
    }

    @Override
    public Long createTheme(Theme theme) {
        return themeDaoImpl.createTheme(theme);
    }

    @Override
    public Long deleteThemeById(long themeId) {
        return themeDaoImpl.deleteTheme(themeId);
    }

    @Override
    public Optional<List<Theme>> getThemes() {
        return themeDaoImpl.getThemes();
    }

}
