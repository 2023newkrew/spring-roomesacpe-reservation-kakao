package nextstep.reservation.service;

import nextstep.reservation.entity.Reservation;
import nextstep.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation create(Reservation reservation) {
        return reservationRepository.create(reservation);
    }

    public Reservation findById(long id) {
        return reservationRepository.findById(id);
    }

    public Boolean delete(long id) {
        return reservationRepository.delete(id);
    }

    public void clear() {
        reservationRepository.clear();
    }
}
