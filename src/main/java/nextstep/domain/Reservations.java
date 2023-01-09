package nextstep.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Reservations {

    private final List<Reservation> reservations;

    public Reservations() {
        reservations = new ArrayList<>();
    }

    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

}
