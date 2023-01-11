package nextstep.domain;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.isSameDateAndTime(date, time));
    }

    public void deleteAll() {
        reservations.clear();
    }

    public void deleteById(Long reservationId) {
        reservations.removeIf(reservation -> reservation.isSameId(reservationId));
    }
}
