package reservation.service;

import org.springframework.stereotype.Service;
import reservation.domain.Reservation;
import reservation.domain.dto.ReservationDto;
import reservation.exception.ReservationException;
import reservation.respository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(ReservationDto reservation) {
        if(reservationRepository.checkReservation(reservation.getDate(), reservation.getTime())){
            throw new ReservationException();
        }
        return reservationRepository.createReservation(reservation);
    }

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.getReservation(reservationId);
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }
}
