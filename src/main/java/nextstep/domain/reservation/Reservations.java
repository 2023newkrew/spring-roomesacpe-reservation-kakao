package nextstep.domain.reservation;

import java.util.HashMap;

public class Reservations {
    public final static HashMap<Long, Reservation> reservations = new HashMap<>();

    public static void add(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public static Reservation get(Long id) {
        return reservations.get(id);
    }

    public static void delete(Long id) {
        reservations.put(id, null);
    }

    public static void removeAll() {
        reservations.clear();
    }
}
