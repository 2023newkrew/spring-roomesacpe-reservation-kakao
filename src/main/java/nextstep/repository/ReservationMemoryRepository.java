package nextstep.repository;

import nextstep.domain.Reservation;
import nextstep.exception.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReservationMemoryRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private Long reservationIdIndex = 1L;



    @Override
    public Reservation save(Reservation newReservation) {
        newReservation.setId(reservationIdIndex++);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    @Override
    public boolean hasReservationAt(LocalDate date, int hour) {
        return reservations.values().stream()
                .anyMatch(reservation -> reservation.startsAt(date, hour));
    }

    @Override
    public boolean existsByThemeId(Long themeId) {
        return reservations.values().stream()
                .anyMatch(reservation -> Objects.equals(reservation.getTheme().getId(), themeId));
    }

    @Override
    public Reservation findById(Long id)  throws ReservationNotFoundException {
        Reservation reservation = reservations.get(id);

        if (reservation == null) {
            throw new ReservationNotFoundException();
        }

        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
