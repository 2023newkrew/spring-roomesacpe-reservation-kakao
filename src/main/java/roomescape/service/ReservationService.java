package roomescape.service;

import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationAppDAO;
import roomescape.dto.ReservationRequest;

@Service
@Transactional
public class ReservationService {

    private ReservationAppDAO reservationAppDAO;

    public ReservationService(ReservationAppDAO reservationAppDAO) {
        this.reservationAppDAO = reservationAppDAO;
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        if (reservationAppDAO.checkSchedule(reservationRequest) == 0) {
            Long id = reservationAppDAO.addReservation(reservationRequest.toReservation());
            return reservationAppDAO.findReservation(id);
        }
        return null;
    }

    public Reservation showReservation(Long id) {
        return reservationAppDAO.findReservation(id);
    }

    public int deleteReservation(Long id) {
        return reservationAppDAO.removeReservation(id);
    }

}
