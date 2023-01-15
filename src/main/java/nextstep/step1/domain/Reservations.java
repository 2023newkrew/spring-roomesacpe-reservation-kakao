package nextstep.step1.domain;

import nextstep.step1.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Reservations {

    private final Map<Long, Reservation> reservations;
    private Long lastId;

    private static final Reservations instance = new Reservations();

    private Reservations(){
        this.lastId = 1L;
        this.reservations = new ConcurrentHashMap<>();
    }

    public static Reservations getInstance(){
        return instance;
    }

    public Long add(Reservation reservation) {
        reservation.setId(getAutoIncrementId());

        reservations.put(reservation.getId(), reservation);
        return reservation.getId();
    }

    public Reservation findById(Long id) {
        return reservations.get(id);
    }

    public void deleteById(Long id){
        Reservation reservation = findById(id);

        if(reservation == null){
            throw new IllegalArgumentException();
        }
        reservations.remove(id);
    }

    private Long getAutoIncrementId(){
        return lastId++;
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    public Reservation findByDateTime(LocalDate date, LocalTime time) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getTime().equals(time)
                        && reservation.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }
}
