package roomescape.service.reservation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

@Service
public class ReservationService implements ReservationServiceInterface {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
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
    }

    private void throwIfExistReservation(Reservation reservation) {
        if (reservationDAO.exist(reservation)) {
            throw new BadRequestException();
        }
    }

    private void throwIfInvalidReservation(Reservation reservation) {
        if (reservation.getDate() == null || reservation.getTime() == null
                || reservation.getName() == null) {
            throw new BadRequestException();
        }
    }

    @Override
    @Transactional
    public Reservation find(Long id) {
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
    public void remove(Long id) {
        throwIfNotExistId(id);
        reservationDAO.remove(id);
    }

    private void throwIfNotExistId(Long id) {
        if(!reservationDAO.existId(id)) {
            throw new BadRequestException();
        }
    }
}
