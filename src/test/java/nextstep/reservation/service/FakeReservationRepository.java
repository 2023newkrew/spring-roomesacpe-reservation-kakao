package nextstep.reservation.service;

import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.Theme;
import nextstep.reservation.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class FakeReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations = new HashMap<>();

    private final Theme theme = new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000);

    private Long reservationIdIndex = 0L;


    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.values()
                .stream()
                .anyMatch(r -> r.getDate()
                        .equals(date) && r.getTime()
                        .equals(time));
    }

    @Override
    public Long insert(Reservation reservation) {
        reservation = new Reservation(
                ++reservationIdIndex,
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                theme
        );
        reservations.put(reservationIdIndex, reservation);

        return reservationIdIndex;
    }

    @Override
    public Reservation getById(Long id) {
        return reservations.get(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return reservations.keySet()
                .removeIf(id::equals);
    }
}
