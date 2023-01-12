package roomescape.repository;

import java.io.Serializable;
import java.util.Optional;

public interface CrudRepository <T, ID extends Serializable> {
    T save(T entity);

    Optional<T> findOne(ID id);

    void delete(ID id);
}
