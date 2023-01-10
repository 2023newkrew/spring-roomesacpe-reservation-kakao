package nextstep.web;

import nextstep.model.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations;

    public ReservationRepository() {
        this.reservations = new ArrayList<>();
    }

    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public Optional<Reservation> findById(long id) {
        return reservations.stream().filter(r -> r.getId() == id).findFirst();
    }

    public void deleteById(Long id) {
        Reservation reservation = findById(id).get();
        reservations.remove(reservation);
    }

    public void deleteAll() {
        reservations.clear();
    }

    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(r -> r.getDate().equals(date) && r.getTime().equals(time));
    }
}
