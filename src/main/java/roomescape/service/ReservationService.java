package roomescape.service;

import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationAppDAO;
import roomescape.dto.ReservationRequest;

@Service
@Transactional
public class ReservationService {

    private ReservationDAO reservationDAO;

    public ReservationService(ReservationAppDAO reservationAppDAO) {
        this.reservationDAO = reservationAppDAO;
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        if (reservationDAO.checkSchedule(reservationRequest) == 0) {
            Long id = reservationDAO.addReservation(reservationRequest.toReservation());
            return reservationDAO.findReservation(id);
        }
        return null;
    }

    public Reservation showReservation(Long id) {
        return reservationDAO.findReservation(id);
    }

    public int deleteReservation(Long id) {
        return reservationDAO.removeReservation(id);
    }

}
