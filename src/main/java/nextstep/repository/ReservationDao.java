package nextstep.repository;

import nextstep.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReservationDao {
    private List<Reservation> reservations = new ArrayList<>();

    public void save(Reservation reservation) {
        reservation.setId((long) (reservations.size() + 1));
        reservations.add(reservation);
    }

    public Reservation findById(Long id) {
        return reservations.get((int) (id - 1));
    }

    public void clear() {
        reservations = new ArrayList<>();
    }

    public void delete(Long id) {
        reservations.remove(id);
    }

    public Reservation findByDateAndTime(LocalDate date, LocalTime time) {
        List<Reservation> result = reservations.stream().filter(reservation -> {
            return reservation.getDate().equals(date)  && reservation.getTime().equals(time);
        }).collect(Collectors.toList());
        if (result.size() != 0) {
            return result.get(0);
        }
        return null;
    }
}
