package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.exception.ReservationException;
import nextstep.repository.ReservationRepository;
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

    public Reservation findById(Long id) throws ReservationException {
        Reservation reservation = reservationRepository.findById(id);
        return reservation;
    }

    public void deleteReservation(Long id) throws ReservationException {
        reservationRepository.deleteById(id);
    }

    public Long createReservation(LocalDate date, LocalTime time, String name, Theme theme)
            throws ReservationException {
        return reservationRepository.save(date, time, name, theme);
    }

    public Long createReservation(Reservation reservation) {
        return this.createReservation(reservation.getDate(), reservation.getTime(),
                reservation.getName(), reservation.getTheme());
    }
}
