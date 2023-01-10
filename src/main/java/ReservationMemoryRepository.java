import nextstep.Reservation;
import nextstep.repository.ReservationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return Optional.empty();
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
