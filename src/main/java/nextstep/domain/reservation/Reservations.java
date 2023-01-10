package nextstep.domain.reservation;

import java.util.ArrayList;
import java.util.List;

public class Reservations {
    public final static List<Reservation> reservations = new ArrayList<>();

    public static void add(Reservation reservation) {
        reservations.add(reservation);
    }
}
