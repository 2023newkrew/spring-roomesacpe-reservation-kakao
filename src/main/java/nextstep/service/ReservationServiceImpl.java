package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.exception.DuplicateReservationException;
import nextstep.exception.NotFoundReservationException;
import nextstep.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    public ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation reserve(Reservation reservation) {
        if (isDuplicateReservation(reservation)) {
            throw new DuplicateReservationException("날짜와 시간이 동일한 예약이 이미 존재합니다.");
        }
        return repository.save(reservation);
    }

    public Reservation findReservation(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundReservationException("예약이 존재하지 않습니다 - id:" + id));
    }

    public boolean cancelReservation(Long id) {
        return repository.delete(id);
    }

    private boolean isDuplicateReservation(Reservation reservation) {
        List<Reservation> confirmedReservations = repository.findAll();
        return confirmedReservations.stream()
                .anyMatch(confirmed ->
                        confirmed.getDate().equals(reservation.getDate()) &&
                                confirmed.getTime().equals(reservation.getTime()));
    }
}
