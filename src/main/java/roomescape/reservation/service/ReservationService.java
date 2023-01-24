package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.TimeTable;
import roomescape.exception.DuplicatedReservationException;
import roomescape.exception.InvalidTimeReservationException;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public Long createReservation(ReservationRequest reservation){
        checkDuplicatedDateAndTime(reservation.getDate(), reservation.getTime());
        checkInvalidTime(reservation.getTime());
        return reservationRepository.save(reservation);
    }

    private void checkInvalidTime(LocalTime time) {
        if(!isInTimeTable(time)){
            throw new InvalidTimeReservationException("유효하지 않은 시간입니다.");
        }
    }

    public boolean isInTimeTable(LocalTime time) {
        for (TimeTable validTime : TimeTable.values()) {
            if (validTime.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    private void checkDuplicatedDateAndTime(LocalDate date, LocalTime time) {
        Optional<Reservation> reservation = reservationRepository.findDuplicatedDateAndTime(date, time);
        if (reservation.isPresent()) {
            throw new DuplicatedReservationException("같은 날짜/시간에 이미 예약이 있습니다.");
        }
    }

    public ReservationResponse findById(String reservationId){
        return ReservationResponse.of(reservationRepository.findById(reservationId));
    }

    public void deleteById(String reservationId){
        reservationRepository.deleteById(reservationId);
    }
}
