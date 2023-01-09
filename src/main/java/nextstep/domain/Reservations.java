package nextstep.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<Reservation> findById(Long reservationId) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), reservationId))
                .findAny();
    }
}
