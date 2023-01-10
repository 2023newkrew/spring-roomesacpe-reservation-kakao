package nextstep.repository;

import nextstep.domain.Reservation;
import org.springframework.stereotype.Repository;

import java.util.*;

public class ReservationMemoryRepository implements ReservationRepository {

    private final Map<Long, Reservation> repository = new HashMap<>();
    private Long id = 0L;

    @Override
    public Reservation save(Reservation reservation) {
        id++;
        repository.put(id, reservation);
        reservation.setId(id);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public boolean delete(Long id) {
        return repository.remove(id) != null;
    }
}
