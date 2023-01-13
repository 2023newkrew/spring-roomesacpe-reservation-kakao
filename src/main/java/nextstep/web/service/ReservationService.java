package nextstep.web.service;

import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.web.exceptions.ErrorCode;
import nextstep.web.exceptions.ReservationException;
import nextstep.web.repository.ReservationDAOImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationDAOImpl reservationDAOImpl;
    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    public ReservationService(ReservationDAOImpl reservationDAOImpl) {
        this.reservationDAOImpl = reservationDAOImpl;
    }

    public Long createReservation(Reservation reservation) {
        List<Reservation> reservationsByDateAndTime = reservationDAOImpl.findByDateAndTime(
                reservation.getDate(), reservation.getTime());
        if (reservationsByDateAndTime.size() > 0) {
            throw new ReservationException(ErrorCode.ALREADY_RESERVATION_EXISTS);
        }

        Reservation newReservation = new Reservation(reservation.getDate(), reservation.getTime(), reservation.getName(), theme);
        return reservationDAOImpl.insertWithKeyHolder(newReservation);
    }

    public Reservation lookupReservation(Long id) {
        try {
            return reservationDAOImpl.findById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new ReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    public Reservation deleteReservation(Long id) {
        try {
            Reservation reservation = reservationDAOImpl.findById(id);
            reservationDAOImpl.delete(id);
            return reservation;
        } catch(EmptyResultDataAccessException e) {
            throw new ReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }
}
