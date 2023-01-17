package nextstep.domain.repository;

import nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class SimpleReservationRepository implements ReservationRepository{
    private final List<Reservation> reservations;
    private final AtomicLong id;

    public SimpleReservationRepository() {
        this.id = new AtomicLong(1L);
        this.reservations = new ArrayList<>();
    }

    @Override
    public Reservation save(Reservation reservation) {
        Long reservationId = id.getAndIncrement();
        Reservation savedReservation = new Reservation(reservationId, reservation);
        reservations.add(savedReservation);
        return savedReservation;
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(reservationId))
                .findAny();
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        return reservations.stream()
                .filter(reservation -> reservation.getThemeId().equals(themeId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameDateAndTime(date, time))
                .count() > 0;
    }

    @Override
    public boolean deleteById(Long reservationId) {
        return reservations.removeIf(reservation -> reservation.isSameId(reservationId));
    }

    @Override
    public void deleteAll() {
        reservations.clear();
        id.set(1L);
    }
}
