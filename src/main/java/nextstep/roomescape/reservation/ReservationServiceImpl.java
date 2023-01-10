package nextstep.roomescape.reservation;

import nextstep.roomescape.reservation.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.create(reservation);
    }

    @Override
    public Reservation findById(long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Boolean delete(long id) {
        return reservationRepository.delete(id);
    }
}
