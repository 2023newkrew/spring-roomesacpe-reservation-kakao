package reservation.respository;

import org.springframework.stereotype.Repository;
import reservation.domain.Reservation;
import reservation.domain.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;

@Repository
public class ReservationRepository {
    private Long idCnt;

    private final Map<Long, Reservation> reservations;

    public ReservationRepository() {
        this.idCnt = 1L;
        this.reservations = new HashMap<>();
    }

    public Long createReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation(idCnt, reservationDto.getDate(), reservationDto.getTime(), reservationDto.getName());
        reservations.put(idCnt, reservation);
        return idCnt++;
    }

    public Reservation getReservation(Long reservationId) {
        return reservations.get(reservationId);
    }

    public void deleteReservation(Long reservationId) {
        reservations.remove(reservationId);
    }

    // 예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다.
    public boolean checkReservation(LocalDate date, LocalTime time) {
        return reservations.values().stream().anyMatch(r -> (r.getDate() == date && r.getTime() == time));
    }
}
