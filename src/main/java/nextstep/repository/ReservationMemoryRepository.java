package nextstep.repository;

import nextstep.domain.Reservation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationMemoryRepository implements ReservationRepository {

    private final Map<Long, Reservation> repository = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0L);

    @Override
    public Reservation save(Reservation reservation) {
        repository.put(id.incrementAndGet(), reservation);
        reservation.setId(id.get());
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
