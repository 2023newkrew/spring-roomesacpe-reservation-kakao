package roomservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomservice.domain.Reservation;
import roomservice.exceptions.exception.DuplicatedReservationException;
import roomservice.exceptions.exception.NonExistentReservationException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservationDao {
//    private JdbcTemplate jdbcTemplate;
    private final Map<Long, Reservation> cache = new HashMap<>();
    private long id = 1L;

/*    public ReservationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
*/
    public long insertReservation(Reservation reservation) {
        validateDuplication(reservation);
        reservation.setId(id);
        cache.put(id, reservation);
        return id++;
    }

    public Reservation selectReservation(long id) {
        validateExistence(id);
        return cache.get(id);
    }

    public void deleteReservation(long id) {
        validateExistence(id);
        cache.remove(id);
    }

    private void validateDuplication(Reservation reservation) {
        for (Reservation cachedReservation : cache.values()) {
            validateDuplicationForSingleReservation(reservation, cachedReservation);
        }
    }

    private void validateDuplicationForSingleReservation(Reservation reservation, Reservation cachedReservation) {
        if (reservation.getTime().equals(cachedReservation.getTime()) &&
                reservation.getDate().equals(cachedReservation.getDate())) {
            throw new DuplicatedReservationException();
        }
    }

    private void validateExistence(long id) {
        if (!cache.containsKey(id)) {
            throw new NonExistentReservationException();
        }
    }
}
