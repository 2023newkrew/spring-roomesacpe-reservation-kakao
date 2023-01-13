package nextstep.reservation.service;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;
import nextstep.reservation.exception.ReservationException;
import nextstep.reservation.exception.ReservationExceptionCode;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static nextstep.reservation.exception.ReservationExceptionCode.DUPLICATE_TIME_RESERVATION;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation registerReservation(Reservation reservation) {
        if (reservationRepository.findByDateAndTime(reservation.getDate(), reservation.getTime()).size() > 0) {
            throw new ReservationException(DUPLICATE_TIME_RESERVATION);
        }
        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public Reservation findById(long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            throw new ReservationException(ReservationExceptionCode.NO_SUCH_RESERVATION);
        }
        return optionalReservation.get();
    }

    public Boolean delete(long id) {
        int deleteRowNumber = reservationRepository.deleteById(id);
        return deleteRowNumber == 1;
    }

    public void clear() {
        reservationRepository.clear();
    }
}
