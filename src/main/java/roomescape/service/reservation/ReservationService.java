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
        throwIfExistReservation(reservation);
        return reservationDAO.create(reservation);
    }

    private void throwIfExistReservation(Reservation reservation) {
        if (reservationDAO.exist(reservation)) {
            throw new BadRequestException();
        }
    }

    @Override
    @Transactional
    public Reservation find(Long id) {
        return reservationDAO.find(id);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        throwIfNotExistId(id);
        reservationDAO.remove(id);
    }

    private void throwIfNotExistId(Long id) {
        if(reservationDAO.find(id) == null) {
            throw new BadRequestException();
        }
    }
}
