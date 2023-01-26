package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.dto.CreateReservationRequest;
import nextstep.repository.reservation.ReservationRepository;
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

    public Reservation findById(Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Long createReservation(LocalDate date, LocalTime time, String name, Long themeId) {
        return reservationRepository.save(date, time, name, themeId);
    }

    public Long createReservation(Reservation reservation) {
        return this.createReservation(reservation.getDate(), reservation.getTime(),
                reservation.getName(), reservation.getThemeId());
    }

    public Long createReservation(CreateReservationRequest request) {
        return this.createReservation(request.getDate(), request.getTime(), request.getName(), request.getThemeId());
    }
}
