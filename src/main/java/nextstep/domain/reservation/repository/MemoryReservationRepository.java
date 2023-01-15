package nextstep.domain.reservation.repository;

import nextstep.domain.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Deprecated
public class MemoryReservationRepository {

    private final List<Reservation> reservations;
    private final AtomicLong id;

    public MemoryReservationRepository() {
        reservations = new ArrayList<>();
        id = new AtomicLong(1L);
    }

    public Reservation save(Reservation reservation) {
        reservation.setId(id.getAndAdd(1L));
        reservations.add(reservation);
        return reservation;
    }

    public Optional<Reservation> findById(Long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(reservationId))
                .findAny();
    }

    public boolean existsByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.isSameThemeAndDateAndTime(themeId, date, time));
    }

    public boolean deleteById(Long reservationId) {
        return reservations.removeIf(reservation -> reservation.isSameId(reservationId));
    }

}
