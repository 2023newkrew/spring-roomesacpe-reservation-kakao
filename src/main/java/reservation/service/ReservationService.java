package reservation.service;

import org.springframework.stereotype.Service;
import reservation.domain.Reservation;
import reservation.handler.exception.DuplicatedObjectException;
import reservation.respository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(Reservation reservation) {
        if (reservationRepository.existReservation(reservation.getDate(), reservation.getTime(), reservation.getThemeId())) {
            throw new DuplicatedObjectException();
        }
        return reservationRepository.createReservation(reservation);
    }

    public Reservation getReservation(long reservationId) {
        return reservationRepository.getReservation(reservationId);
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }
}
