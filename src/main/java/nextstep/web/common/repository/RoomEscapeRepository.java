package nextstep.web.common.repository;

import java.util.List;

public interface RoomEscapeRepository<T> {

    T findById(Long id);

    Long save(T entity);

    void deleteById(Long id);

    List<T> findAll();
}
