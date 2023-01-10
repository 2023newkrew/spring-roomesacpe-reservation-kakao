package reservation.service;

import reservation.model.domain.Theme;
import org.springframework.stereotype.Service;
import reservation.model.domain.Reservation;
import reservation.model.dto.RequestReservation;
import reservation.util.exception.DuplicateException;
import reservation.respository.ReservationRepository;
import reservation.util.exception.NotFoundException;

import static reservation.util.ErrorStatus.RESERVATION_DUPLICATED;
import static reservation.util.ErrorStatus.RESERVATION_NOT_FOUND;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(RequestReservation requestReservation) {
        if(reservationRepository.existByDateTime(requestReservation.getDate(), requestReservation.getTime())){
            throw new DuplicateException(RESERVATION_DUPLICATED);
        }
        Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);
        return reservationRepository.saveReservation(requestReservation, theme);
    }

    public Reservation getReservation(Long id) {
        if(!reservationRepository.existById(id)){
            throw new NotFoundException(RESERVATION_NOT_FOUND);
        }
        return reservationRepository.findReservationById(id);
    }

    public void deleteReservation(Long id) {
        if(!reservationRepository.existById(id)){
            throw new NotFoundException(RESERVATION_NOT_FOUND);
        }
        reservationRepository.deleteReservationById(id);
    }
}
