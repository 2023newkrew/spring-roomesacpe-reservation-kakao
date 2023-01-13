package nextstep.reservation.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.reservation.entity.Theme;

public class ThemeMemoryRepository implements ThemeRepository{

    Map<Long, Theme> repository = new HashMap<>();
    AtomicLong counter = new AtomicLong();

    @Override
    public Long add(Theme theme) {
        theme.setId(counter.incrementAndGet());
        repository.put(theme.getId(), theme);
        return theme.getId();
    }

    @Override
    public Optional<Theme> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<Theme> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public boolean delete(Long id) {
        return repository.remove(id) != null;
    }
}
