package nextstep.main.java.nextstep.mvc.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, V> {
    Long save(T request);
    Optional<V> findById(Long id);
    void deleteById(Long id);
    List<V> findAll();
    void update(Long id, T request);
    Boolean existsById(Long id);
}
