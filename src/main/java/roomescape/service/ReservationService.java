package roomescape.service;

import nextstep.Reservation;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        if (reservationDAO.checkSchedule(reservationRequest) == 0) {
            return reservationDAO.addReservation(reservationRequest);
        }
        return null;
    }

    public Reservation showReservation(Long id) {
        List<Reservation> reservations = reservationDAO.findReservation(id);
        if (reservations.size() == 0) {
            return null;
        }
        return reservations.get(0);
    }

    public int deleteReservation(Long id) {
        return reservationDAO.removeReservation(id);
    }

}
