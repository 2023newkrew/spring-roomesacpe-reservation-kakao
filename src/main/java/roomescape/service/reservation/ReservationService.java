package roomescape.service.reservation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dao.theme.ThemeDAO;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

@Service
public class ReservationService implements ReservationServiceInterface {

    private final ReservationDAO reservationDAO;
    private final ThemeDAO themeDAO;

    public ReservationService(ReservationDAO reservationDAO, ThemeDAO themeDAO) {
        this.reservationDAO = reservationDAO;
        this.themeDAO = themeDAO;
    }

    @Override
    @Transactional
    public Long create(Reservation reservation) {
        validateCreateReservation(reservation);
        Long id = reservationDAO.create(reservation);
        throwIfNull(id);
        return id;
    }

    private void validateCreateReservation(Reservation reservation) {
        throwIfInvalidReservation(reservation);
        throwIfExistReservation(reservation);
        throwIfInvalidReservationThemeId(reservation);
    }

    private void throwIfExistReservation(Reservation reservation) {
        if (reservationDAO.exist(reservation)) {
            throw new BadRequestException();
        }
    }

    private void throwIfInvalidReservation(Reservation reservation) {
        if (reservation.getDate() == null || reservation.getTime() == null
                || reservation.getName() == null || reservation.getThemeId() == null) {
            throw new BadRequestException();
        }
    }

    private void throwIfInvalidReservationThemeId(Reservation reservation) {
        if (!themeDAO.existId(reservation.getThemeId())) {
            throw new BadRequestException();
        }
    }

    @Override
    @Transactional
    public Reservation find(long id) {
        Reservation reservation = reservationDAO.find(id);
        throwIfNull(reservation);
        return reservation;
    }

    private <T> void throwIfNull(T t) {
        if (t == null) {
            throw new BadRequestException();
        }
    }

    @Override
    @Transactional
    public void remove(long id) {
        throwIfNotExistId(id);
        reservationDAO.remove(id);
    }

    private void throwIfNotExistId(long id) {
        if(!reservationDAO.existId(id)) {
            throw new BadRequestException();
        }
    }
}
