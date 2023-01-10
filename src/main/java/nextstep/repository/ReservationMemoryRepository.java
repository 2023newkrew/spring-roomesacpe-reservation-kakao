package nextstep.repository;

import nextstep.dto.Reservation;
import nextstep.dto.Theme;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReservationMemoryRepository implements ReservationRepository{

    private long id = 1;
    private final Map<Long, Reservation> repository = new HashMap<>();
//    private Theme theme = new Theme("워너고홈", "병맛", 29_000);

    @Override
    public Reservation create(Reservation reservation) {

        reservation.setId(id);
//        reservation.setTheme(theme);
        repository.put(id, reservation);
        id++;

        return reservation;
    }

    @Override
    public Reservation find(long id) {
        return repository.get(id);
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public boolean delete(long id) {
        return repository.remove(id) != null;
    }
}
