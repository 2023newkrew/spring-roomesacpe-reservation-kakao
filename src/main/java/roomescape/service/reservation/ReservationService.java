package roomescape.service.reservation;

import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.reservation.ReservationDAO;
import roomescape.dto.Reservation;
import roomescape.exception.BadRequestException;

public class ReservationService implements ReservationServiceInterface {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @Override
    @Transactional
    public Long create(Reservation reservation) {
        if (reservationDAO.exist(reservation)) {
            throw new BadRequestException();
        }
        return reservationDAO.create(reservation);
    }
}
