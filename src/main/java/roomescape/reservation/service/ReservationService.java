package roomescape.reservation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(Reservation reservation){
        checkDuplicatedDateAndTime(reservation.getDate(), reservation.getTime());
        return reservationRepository.save(reservation);
    }

    private void checkDuplicatedDateAndTime(LocalDate date, LocalTime time) {
        boolean exists = Boolean.TRUE.equals(reservationRepository.findDuplicatedDateAndTime(date, time));
        if (exists) {
            ResponseEntity.badRequest().body("같은 시간에 이미 예약이 있습니다!");
        }
    }

    public Reservation findById(String reservationId){
        return reservationRepository.findById(reservationId);
    }

    public void deleteById(String reservationId){
        reservationRepository.deleteById(reservationId);
    }
}
