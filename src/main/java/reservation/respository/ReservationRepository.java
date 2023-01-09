package reservation.respository;

import org.springframework.stereotype.Repository;
import reservation.domain.Reservation;
import reservation.domain.dto.ReservationDto;

import java.util.ArrayList;
import java.util.List;
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

    public void createReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation(idCnt, reservationDto.getDate(), reservationDto.getTime(), reservationDto.getName());
        reservations.put(idCnt, reservation);
    }

    public Reservation getReservation(Long reservation_id) {
        return reservations.getOrDefault(reservation_id, null);
    }
}
