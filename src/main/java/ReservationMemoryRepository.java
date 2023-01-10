import nextstep.Reservation;
import nextstep.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

public class ReservationMemoryRepository implements ReservationRepository {
    @Override
    public Reservation save(Reservation reservation) {
        return null;
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
