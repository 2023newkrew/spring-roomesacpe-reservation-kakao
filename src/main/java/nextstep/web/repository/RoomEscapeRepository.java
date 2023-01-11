package nextstep.web.repository;

public interface RoomEscapeRepository<T> {

    T findById(Long id);

    Long save(T entity);

    void deleteById(Long id);
}
