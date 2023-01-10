package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.repository.ReservationRepository;

import java.util.List;

public class ReservationService {
    
    private final ReservationRepository repository;
    
    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation reserve(Reservation reservation) {
        if (hasSameDateAndTimeReservation(reservation)) {
            throw new IllegalArgumentException("날짜와 시간이 동일한 예약이 이미 존재합니다.");
        }
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
