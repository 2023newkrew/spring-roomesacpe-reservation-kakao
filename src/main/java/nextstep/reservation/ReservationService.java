package nextstep.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation create(LocalDate date, LocalTime time, String name, Theme theme) {
        return reservationRepository.create(date, time, name, theme);
    }

    public Reservation findById(long id) {
        return reservationRepository.findById(id);
    }

    public Boolean delete(long id) {
        return reservationRepository.delete(id);
    }
}
