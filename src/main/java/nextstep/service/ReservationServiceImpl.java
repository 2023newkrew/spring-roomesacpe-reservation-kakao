package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.repository.ReservationRepository;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private static final Theme ROOM_ESCAPE_THEME = new Theme("검은방", "밀실 탈출", 30_000);
    private final ReservationRepository repository;

    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation reserve(Reservation reservation) {
        if (hasSameDateAndTimeReservation(reservation)) {
            throw new IllegalArgumentException("날짜와 시간이 동일한 예약이 이미 존재합니다.");
        }
        reservation.setTheme(ROOM_ESCAPE_THEME);
        return repository.save(reservation);
    }

    private boolean hasSameDateAndTimeReservation(Reservation reservation) {
        List<Reservation> confirmedReservations = repository.findAll();
        return confirmedReservations.stream()
                .anyMatch(confirmed ->
                        confirmed.getDate().equals(reservation.getDate()) &&
                                confirmed.getTime().equals(reservation.getTime()));
    }

    public Reservation findReservation(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public boolean cancelReservation(Long id) {
        return repository.delete(id);
    }
}
